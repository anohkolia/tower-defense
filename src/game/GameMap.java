package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        double startY = 350 - (height  / 2.0) * tileSize;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Tile tile = grid[row][col];

                // Calaule de la position de chaque case
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
        return null;
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
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
