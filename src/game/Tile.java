package game;

import ui.StdDraw;
import java.awt.Color;

public class Tile {
    private final char type; // Type de la case ('S', 'R', 'C', etc.)
    private boolean occupe;      // Indique si la case est occupée par une tour
    //private Tower tower;           // Tour placée sur la case (null si aucune)
    private double x, y; // Position de la case
    private double width, height; // Taille de la case

    public Tile(char type) {
        this.type = type;
        this.occupe = false;
        //this.tower = null;
    }

    public char getType() {
        return type;
    }

    public boolean isOccupe() {
        return occupe;
    }

    /* public Tower getTower() {
        return tower;
    } */

    /* public void placeTower(Tower tower) {
        if (type != 'C') {
            throw new IllegalArgumentException("Une tour ne peut être placée que sur une case constructible (C).");
        }
        if (occupied) {
            throw new IllegalStateException("Cette case est déjà occupée par une tour.");
        }
        this.tower = tower;
        this.occupied = true;
    } */

    public void removeTower() {
        //this.tower = null;
        this.occupe = false;
    }

    public void draw(double x, double y, double halfSize) {
        // Dessine la case avec la couleur correspondant à son type
        switch (type) {
            case 'S': StdDraw.setPenColor(Color.RED); break;             // Spawn
            case 'B': StdDraw.setPenColor(Color.ORANGE); break;          // Base
            case 'R': StdDraw.setPenColor(new Color(194, 178, 128)); break; // Route
            case 'C': StdDraw.setPenColor(Color.LIGHT_GRAY); break;      // Constructible
            case 'X': StdDraw.setPenColor(new Color(11, 102, 35)); break; // Décor
            default: StdDraw.setPenColor(Color.BLACK); break;           // Erreur
        }
        // Dessine la case
        StdDraw.filledSquare(x, y, halfSize);

        // Si une tour est placée sur cette case, dessine la tour
        /* if (occupied && tower != null) {
            tower.draw(x, y); // Méthode draw() de Tower pour dessiner la tour
        } */
    }

    public double getCenterX() {
        return 0; // A modifier
    }

    public double getCenterY() {
        return 0; // A modifier
    }
}
