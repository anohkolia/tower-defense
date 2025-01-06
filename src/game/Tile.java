package game;

import java.awt.Color;
import ui.StdDraw;

public class Tile {
    private final char type; // Type de la case ('S', 'R', 'C', etc.)
    private boolean occupe;      // Indique si la case est occupée par une tour
    private double x, y; // Position de la case
    private double size; // Taille de la case
    private int row, col; // Position de la case dans la grille
    private GameMap grid; // Grille de jeu
    private char[][] tempGrid;

    public Tile(GameMap grid, int row, int col, char type) {
        this.type = type;
        this.occupe = false;
        this.grid = grid;
        this.row = row;
        this.col = col;
    }

    // initialisation de la position et de la taille de la case
    public void setPosition(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void removeTower() {
        //this.tower = null;
        this.occupe = false;
    }

    public void draw() {
        // Dessine la case avec la couleur correspondant à son type
        switch (getType()) {
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
    }

    // Vérifie si les coordonnées (mouseX, mouseY) sont à l'intérieur de la case
    public boolean isInside(double mouseX, double mouseY) {
        double halfSize = size / 2;
        return mouseX >= (x - halfSize) && mouseX <= (x + halfSize) &&
               mouseY >= (y - halfSize) && mouseY <= (y + halfSize);
    }

    public boolean contains(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size;
    }
    
    public boolean isOccupe() {
        return occupe;
    }

    public double getCenterX() {
        return x; // Retourne la position X du centre de la case
    }

    public double getCenterY() {
        return y; // Retourne la position Y du centre de la case
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
        return this.type;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

}
