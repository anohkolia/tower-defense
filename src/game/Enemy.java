package game;

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

    private Tile getCaseSuivante(Tile tile) {
        // Logique pour récupérer la prochaine case 'R' à partir de la case actuelle

        return null; // A modifier
        //return tile.getNext();
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