package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// Cette classe gère la lecture et l'affichage de la carte de jeu
public class GameMap {
    private final int width;
    private final int height;
    private final Tile[][] grid;

    public GameMap(String mapFile) {
        char[][] tempGrid = readMap(mapFile);
        this.width = tempGrid[0].length;
        this.height = tempGrid.length;
        this.grid = new Tile[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Tile(this, row, col, tempGrid[row][col]);
            }
        }

        verifierCarte();
    }

    private char[][] readMap(String mapFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
            return br.lines().map(String::toCharArray).toArray(char[][]::new);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier de carte : " + mapFile);
            e.printStackTrace();
            return new char[0][0];
        }
    }

    public Tile getTile(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            throw new IndexOutOfBoundsException("Coordonnées invalides.");
        }
        return grid[row][col];
    }

    public void draw() {
        double tileSize = 700.0 / Math.max(width, height); // Ajustement de la taille des cases en fonction de la taille de la carte
        double startX = 350 - (width / 2.0) * tileSize;
        double startY = 350 - (height / 2.0) * tileSize;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Tile tile = grid[row][col];

                // Calcule de la position de chaque case
                double x = startX + col * tileSize + tileSize / 2;
                double y = startY + (grid.length - row - 1) * tileSize + tileSize / 2;

                // Définit la position et la taille de la case
                tile.setPosition(x, y, tileSize);

                // Dessine la case
                tile.draw();
            }
        }
    }

    // Implémentation de la méthode getCaseDepart() pour obtenir la case de départ 'S' de l'ennemi
    public Tile getCaseDepart() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].getType() == 'S') {
                    return grid[row][col];
                }
            }
        }
        throw new IllegalStateException("Aucune case 'S' trouvée sur la carte.");
    }

    // Implémentation de la méthode getCaseArrivee() pour obtenir la case d'arrivée 'B' de l'ennemi
    public Tile getCaseArrivee() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].getType() == 'B') {
                    return grid[row][col];
                }
            }
        }
        throw new IllegalStateException("Aucune case 'B' trouvée sur la carte.");
    }

    // -------------------------------------------------------------------------------

    public List<Tile> calculerCheminVersBase() {
        Tile caseDepart = getCaseDepart();
        Tile caseArrivee = getCaseArrivee();

        if (caseDepart == null || caseArrivee == null) {
            throw new IllegalStateException("Les cases 'S' ou 'B' ne sont pas définies sur la carte.");
        }

        // Utilisation de BFS pour trouver le chemin
        Queue<Tile> queue = new LinkedList<>();
        Map<Tile, Tile> cameFrom = new HashMap<>();
        queue.add(caseDepart);
        cameFrom.put(caseDepart, null);

        while (!queue.isEmpty()) {
            Tile current = queue.poll();

            if (current == caseArrivee) {
                break; // Chemin trouvé
            }

            for (Tile voisin : getVoisins(current)) {
                if (!cameFrom.containsKey(voisin) && (voisin.getType() == 'R' || voisin.getType() == 'B')) {
                    queue.add(voisin);
                    cameFrom.put(voisin, current);
                }
            }
        }

        if (!cameFrom.containsKey(caseArrivee)) {
            throw new IllegalStateException("Aucun chemin trouvé entre 'S' et 'B'.");
        }

        // Reconstruire le chemin
        List<Tile> chemin = new ArrayList<>();
        Tile current = caseArrivee;
        while (current != null) {
            chemin.add(0, current); // Ajoute au début de la liste pour construire le chemin
            current = cameFrom.get(current);
        }

        return chemin;
    }

    private List<Tile> getVoisins(Tile tile) {
        List<Tile> voisins = new ArrayList<>();
        int row = tile.getRow();
        int col = tile.getCol();

        if (row > 0)
            voisins.add(grid[row - 1][col]); // Haut
        if (row < height - 1)
            voisins.add(grid[row + 1][col]); // Bas
        if (col > 0)
            voisins.add(grid[row][col - 1]); // Gauche
        if (col < width - 1)
            voisins.add(grid[row][col + 1]); // Droite

        return voisins;
    }

    // Vérifie que la carte contient une case de départ S et une case d'arrivée B
    private void verifierCarte() {
        int nbSpawn = 0;
        int nbBase = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                char type = grid[row][col].getType();
                if (type == 'S')
                    nbSpawn++;
                if (type == 'B')
                    nbBase++;
            }
        }

        if (nbSpawn != 1 || nbBase != 1) {
            throw new IllegalStateException("La carte doit contenir exactement une case 'S' et une case 'B'.");
        }
    }

    // ------------------------------------------------------------------------------------------

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getGrid() {
        return grid;
    }

}
