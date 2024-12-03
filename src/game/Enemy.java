package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import ui.StdDraw;
public class Enemy {
    private double x, y; // Position de actuelle l'ennemi
    private  int pv;
    private  double speed; // Vitesse de déplacement de l'ennemi
    private  int reward;
    private Tile caseActuelle; // Case sur laquelle se trouve l'ennemi
    private Tile caseCible; // Case cible vers laquelle se dirige l'ennemi

    public Enemy(Tile caseDepart, double speed, int pv) {
        this.caseActuelle = caseDepart;
        this.caseCible = caseDepart;
        this.x = caseDepart.getCenterX();
        this.y = caseDepart.getCenterY();
        this.speed = speed;
        this.pv = pv;
    }

    public void seDeplacer(double deltaTimeSecond) {
        // Il n'y a pas de case suivante, l'ennemi est arrivé à destination
        if (caseCible == null) {
            return;
        }

        // Calcul de la distance entre l'ennemi et la case cible
        double dx = caseCible.getCenterX() - x;
        double dy = caseCible.getCenterY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Vérification si l'ennemi est arrivé à la case cible
        if (distance < speed * deltaTimeSecond) {
            // Mettre à jour la position de l'ennemi et cible la case suivante
            x = caseCible.getCenterX();
            y = caseCible.getCenterY();
            caseActuelle = caseCible;
            caseCible = getCaseSuivante(caseActuelle); // Récupère la case suivante
        } else {
            // Déplacement de l'ennemi vers la case cible
            x += (dx / distance) * speed * deltaTimeSecond;
            y += (dy / distance) * speed * deltaTimeSecond;
        }

    }

    // Implémentation de la méthode getCaseSuivante() pour obtenir la case suivante 'R' proche de la case actuelle sur laquelle l'ennemi vas se déplacer
    private Tile getCaseSuivante(Tile tile) {
        // Utilisation de BFS pour trouver les case 'R' voisines pour arriver à la case 'B'
        Queue<Tile> queue = new LinkedList<>();
        Map<Tile, Tile> cameFrom = new HashMap<>();
        queue.add(tile);
        cameFrom.put(tile, null);

        while (!queue.isEmpty()) {
            Tile current = queue.poll();

            if (current.getType() == 'B') {
                return reconstructPath(cameFrom, tile, current);
            }
            
            for (Tile neighbor : getVoisins(current)) {
                if (!cameFrom.containsKey(neighbor) && neighbor.getType() != 'C') {
                    queue.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return null;
    }

    private Tile reconstructPath(Map<Tile, Tile> cameFrom, Tile start, Tile goal) {
        Tile current = goal;
        while (cameFrom.get(current) != start) {
            current = cameFrom.get(current);
        }
        return current;
    }

    private List<Tile> getVoisins(Tile tile) {
        List<Tile> voisins = new ArrayList<>();
        int row = tile.getRow();
        int col = tile.getCol();
        GameMap grid = tile.getGrid();

        if (row > 0) voisins.add(grid.getTile(row - 1, col)); // Voisin du haut
        if (row < grid.getHeight() - 1) voisins.add(grid.getTile(row + 1, col)); // Voisin du bas
        if (col > 0) voisins.add(grid.getTile(row, col - 1)); // Voisin de gauche
        if (col < grid.getWidth() - 1) voisins.add(grid.getTile(row, col + 1)); // Voisin de droite

        return voisins;
    }

    public void subirDegats(int degats) {
        pv -= degats;
    }

    public boolean estMort() {
        return pv <= 0;
    }

    public void draw() {
        // Dessine l'ennemi à la position (x, y)
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(x, y, 10); // Dessine un cercle rouge de rayon 10
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getPv() {
        return pv;
    }

    public double getSpeed() {
        return speed;
    }

    public int getReward() {
        return reward;
    }
}
