package main;

import game.GameMap;
import ui.StdDraw;

// Gestion de la boucle de jeu et initialisation de la carte.
public class Game {
    
    private GameMap map;

    public void launch() {
        init();
        long previousTime = System.currentTimeMillis();

        while (isGameRunning()) {
            long currentTime = System.currentTimeMillis();
            double deltaTimeSecond = (double) (currentTime - previousTime) / 1000;
            previousTime = currentTime;
            
            update(deltaTimeSecond);            
        }
    }

    private void init() {
        StdDraw.setCanvasSize(1024, 720);
        StdDraw.setXscale(-12, 1012);
        StdDraw.setYscale(-10, 710);
        StdDraw.enableDoubleBuffering();

        map = new GameMap("10-10.mtp"); // Nom de la carte, modifiable selon le fichier à charger
    }

    private boolean isGameRunning() {
        return true; // TODO: Modifiable plus tard pour gérer les conditions d'arrêt
    }

    private void update(double deltaTimeSecond) {
        StdDraw.clear();
        map.draw(); // Appel la méthode draw de pour afficher la carte
        // TODO: A l'avenir, mettre à jour ici les positions des ennemis, des tours, etc.
        StdDraw.show();
    }


}
