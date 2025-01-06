package tours;

import java.util.List;

import game.Enemy;
import game.Tile;
import game.Tower;
import ui.StdDraw;

public class WindCaster extends Tower {

    public WindCaster(Tile position) {
        super(position, 6, 10, 50, 30, 30, 1.5, true);
    }

    @Override
    public void attaquer(List<Enemy> ennemis) {
        for (Enemy ennemi : ennemis) {
            if (estDansPortee(ennemi)) {
                ennemi.subirDegats(getDegats());
                break; // Attaque un ennemi à la fois
            }
        }
    }

    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(getPosition().getCenterX(), getPosition().getCenterY(), 20);
    }

    @Override
    public void update(double deltaTimeSecond, List<Enemy> ennemis) {
        attaquer(ennemis);
    }

    @Override
    public boolean estDansPortee(Enemy ennemi) {
        double distance = Math.sqrt(Math.pow(ennemi.getX() - getPosition().getCenterX(), 2)
                + Math.pow(ennemi.getY() - getPosition().getCenterY(), 2));
        return distance <= getPortee();
    }
}
