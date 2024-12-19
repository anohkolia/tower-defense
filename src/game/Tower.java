package game;

import java.util.List;

import ui.StdDraw;

public class Tower {
    private Tile position; // Position de la tour
    private int portee; // Portée de la tour
    private int degats; // Dégâts infligés par la tour
    private int cout; // Coût de la tour

    public Tower(Tile position, int portee, int degats, int cout) {
        this.position = position;
        this.portee = portee;
        this.degats = degats;
        this.cout = cout;
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

    public void attaquer(List<Enemy> ennemis) {
        for (Enemy ennemi : ennemis) {
            double distance = Math.sqrt(Math.pow(ennemi.getX() - position.getCenterX(), 2)
                            + Math.pow(ennemi.getY() - position.getCenterY(), 2));
            if (distance <= portee) {
                ennemi.subirDegats(degats);
                break; // On attaque un seul ennemi à la fois
            }
        }
    }

    // Dessine la tour
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledSquare(position.getCenterX(), position.getCenterY(), 10); // Dessin de la tour
    }
}
