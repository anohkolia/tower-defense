package game;

import java.util.List;
import ui.StdDraw;

public class Tower {
    private Tile position; // Position de la tour
    private int portee; // Portée de la tour
    private int degats; // Dégâts infligés par la tour
    private int cout; // Coût de la tour
    private int pv; // Points de vie de la tour
    // Temps restant avant de pouvoir attaquer à nouveau
    private double tempsRecharge;
    // Temps entre deux attaques
    private double vitesseAttaque;

    public Tower(Tile position, int portee, int degats, int cout, int pv, double vitesseAttaque) {
        this.position = position;
        this.portee = portee;
        this.degats = degats;
        this.cout = cout;
        this.pv = pv;
        this.vitesseAttaque = vitesseAttaque;
        this.tempsRecharge = 0;
    }

    public Tower(double vitesseAttaque) {
        this.vitesseAttaque = vitesseAttaque;
        this.tempsRecharge = 0;
    }

    public void update(double deltaTimeSecond, List<Enemy> ennemis) {
        if (tempsRecharge > 0) {
            tempsRecharge -= deltaTimeSecond;
        }

        if (tempsRecharge <= 0) {
            Enemy ennemiCible = trouverCible(ennemis);
            if (ennemiCible != null) {
                attaquer(ennemis);
                tempsRecharge = vitesseAttaque;
            }
        }
    }

    public Enemy trouverCible(List<Enemy> ennemis) {
        // Logique pour trouver l'ennemi dans la portée
        Enemy ennemieLaPlusProche = null; // Ennemi le plus proche
        double minDistance = Double.MAX_VALUE;

        for (Enemy ennemi : ennemis) {
            if (estDansPortee(ennemi)) {
                double distance = Math.sqrt(Math.pow(ennemi.getX() - position.getCenterX(), 2)
                        + Math.pow(ennemi.getY() - position.getCenterY(), 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    ennemieLaPlusProche = ennemi;
                }
            }
        }

        return ennemieLaPlusProche;
    }

    public boolean estDansPortee(Enemy ennemi) {
        double distance = Math.sqrt(Math.pow(ennemi.getX() - position.getCenterX(), 2)
                + Math.pow(ennemi.getY() - position.getCenterY(), 2));
        return distance <= portee;
    }

    public void attaquer(List<Enemy> ennemis) {
        for (Enemy ennemi : ennemis) {
            if (estDansPortee(ennemi)) {
                ennemi.subirDegats(10);
                break; // On attaque un seul ennemi à la fois
            }
        }
    }

    public void barreDeVie() {
        
    }

    /**
     * Méthode pour faire subir des dégâts à la tour
     * @param degats Les dégâts infligés à la tour
     */
    public void subirDegats(int degats) {
        this.pv -= degats;

    }

    // Dessine la tour
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledSquare(position.getCenterX(), position.getCenterY(), 10); // Dessin de la tour
    }

    public boolean estDetruite() {
        return pv <= 0;
    }

    public Tile getPosition() {
        return position;
    }

    public int getPortee() {
        return portee;
    }

    public int getDegats() {
        return degats;
    }

    public int getCout() {
        return cout;
    }

    public double getTempsRecharge() {
        return tempsRecharge;
    }
}
