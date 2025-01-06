package game;

import java.util.List;
import ui.StdDraw;

public class Tower {
    private Tile position; // Position de la tour
    private int portee; // Portée de la tour
    private int degats; // Dégâts infligés par la tour
    private int cout; // Coût de la tour
    private int pv; // Points de vie de la tour
    private int pvMax; // Points de vie maximum de la tour
    private boolean tourPosee; // Indique si la tour est détruite
    // Temps restant avant de pouvoir attaquer à nouveau
    private double tempsRecharge;
    // Temps entre deux attaques
    private double vitesseAttaque;

    public Tower(Tile position, int portee, int degats, int cout, int pv, int pvMax, double vitesseAttaque, boolean tourPosee) {
        this.position = position;
        this.portee = portee;
        this.degats = degats;
        this.cout = cout;
        this.pv = pv;
        this.pvMax = pvMax;
        this.vitesseAttaque = vitesseAttaque;
        this.tempsRecharge = 0;
        this.tourPosee = tourPosee;
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
                ennemi.subirDegats(degats);
                break; // On attaque un seul ennemi à la fois
            }
        }
    }

    public void barreDeVie() {
        double ratioVie = Math.max(0, (double) pv / pvMax); // Ratio de vie
        double barreWidth = 62; // Largeur totale de la barre
        double barreHeight = 5; // Hauteur de la barre
        double barreX = position.getCenterX(); // Position X de la barre
        double barreY = position.getCenterY() + 27; // Position Y de la barre (au-dessus de la tour)

        // Largeur de la barre verte (selon la vie restante)
        double barreVieWidth = barreWidth * ratioVie;

        // Dessine la barre rouge (arrière-plan, vie totale)
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledRectangle(barreX, barreY, barreWidth / 2, barreHeight / 2);

        // Dessine la barre verte (vie restante)
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(barreX - (barreWidth - barreVieWidth) / 2, barreY, barreVieWidth / 2, barreHeight / 2);

        // Ajout d'un contour noir autour de la barre
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(barreX, barreY, barreWidth / 2, barreHeight / 2);
    }

    /**
     * Méthode pour faire subir des dégâts à la tour
     * @param degats Les dégâts infligés à la tour
     */
    public void subirDegats(int degats) {
        this.pv -= degats;
    }

    /**
     * Méthode pour détruire la tour
     */
    public void tourDetruire() {
        this.tourPosee = false;
    }

    // Dessine la tour
    public void draw() {
        // Dessine le contour jaune si la tour est posée
        if (tourPosee) {
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.rectangle(position.getCenterX(), position.getCenterY(), 35, 35); // Contour jaune légèrement plus grand que la case
        }

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.filledCircle(position.getCenterX(), position.getCenterY(), 20); // Dessine un cercle rouge de rayon 10

        
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

    public double getVitesseAttaque() {
        return vitesseAttaque;
    }

    public int getPvMax() {
        return pvMax;
    }

    public boolean getTourPosee() {
        return tourPosee;
    }

    
}
