package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ui.StdDraw;

// Cette classe gère la lecture et l'affichage de la carte de jeu
public class GameMap {
    private final int width;
    private final int height;
    private final char[][] grid;

    public GameMap(String mapFile) {
        char[][] tempGrid = readMap(mapFile);
        this.width = tempGrid[0].length;
        this.height = tempGrid.length;
        this.grid = tempGrid;
    }

    private char[][] readMap(String mapFile) {
        try (BufferedReader br = new BufferedReader(new FileReader("../resources/maps/" + mapFile))) {
            return br.lines().map(String::toCharArray).toArray(char[][]::new);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier de carte : " + mapFile);
            e.printStackTrace();
            return new char[0][0];
        }
    }

    public void draw() {
        double tileSize = 700.0 / Math.max(width, height); // Ajustement de la taille des cases en fonction de la taille de la carte
        double startX = 350 - (width / 2.0) * tileSize;
        double startY = 350 - (height  / 2.0) * tileSize;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                char tileType = grid[row][col];
                drawTile(startX + col * tileSize, startY + (height - row - 1) * tileSize, tileSize / 2, tileType);
            }
        }
    }

    private void drawTile(double x, double y, double halfSize, char tileType) {
        switch (tileType) {
            case 'S':
                StdDraw.setPenColor(255, 0, 0); // Rouge
                break;
            case 'B':
                StdDraw.setPenColor(255, 165, 0); // Orange
                break;
            case 'R':
                StdDraw.setPenColor(194, 178, 128); // Marron
                break;
            case 'C':
                StdDraw.setPenColor(211, 211, 211); // Gris clair
                break;
            case 'X':
                StdDraw.setPenColor(11, 102, 35); // Vert foncé
                break;
            default:
                StdDraw.setPenColor(0, 0, 0); // Noir pour erreur
                break;
        }
        StdDraw.filledSquare(x, y, halfSize);
    }

}
