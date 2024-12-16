package game;

import java.awt.Color;
import ui.StdDraw;

public class Tile {
    private final char type; // Type de la case ('S', 'R', 'C', etc.)
    private boolean occupe;      // Indique si la case est occupée par une tour
    //private Tower tower;           // Tour placée sur la case (null si aucune)
    private double x, y; // Position de la case
    private double size; // Taille de la case
    private int row, col; // Position de la case dans la grille
    private GameMap grid; // Grille de jeu

    public Tile(GameMap grid, int row, int col, char type) {
        this.type = type;
        this.occupe = false;
        this.grid = grid;
        this.row = row;
        this.col = col;
        //this.tower = null;
    }

    // initialisation de la position et de la taille de la case
    public void setPosition(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
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

    public void draw() {
        // Dessine la case avec la couleur correspondant à son type
        switch (type) {
            case 'S': StdDraw.setPenColor(Color.RED); break;             // Spawn
            case 'B': StdDraw.setPenColor(Color.YELLOW); break;          // Base
            case 'R': StdDraw.setPenColor(new Color(194, 178, 128)); break; // Route
            case 'C': StdDraw.setPenColor(Color.LIGHT_GRAY); break;      // Constructible
            case 'X': StdDraw.setPenColor(new Color(11, 102, 35)); break; // Décor
            default: StdDraw.setPenColor(Color.BLACK); break;           // Erreur
        }
        // Dessine la case
        StdDraw.filledSquare(x, y, size / 2);

        // Dessine le contour de la case
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.square(x, y, size / 2);

        // Si une tour est placée sur cette case, dessine la tour
        /* if (occupe && tower != null) {
            tower.draw(x, y); // Méthode draw() de Tower pour dessiner la tour
        } */
    }

    public double getCenterX() {
        return col * 70 / 2; // Retourne la position X du centre de la case
    }

    public double getCenterY() {
        return row * 70 / 2; // Retourne la position Y du centre de la case
    }

    public GameMap getGrid() {
        return grid;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getType() {
        return type;
    }

}
