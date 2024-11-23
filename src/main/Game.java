package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.ChargementVague;
import game.Enemy;
import game.GameMap;
import game.Tile;
import game.VagueEnnemi;
import ui.StdDraw;

// Gestion de la boucle de jeu et initialisation de la carte.
public class Game {
    private GameMap map;
    private VagueEnnemi vagueEncours; // Vague d'ennemis actuellement en cours
    private List<Enemy> ennemiesActifs;

    public void launch() {
        init();
        long previousTime = System.currentTimeMillis();

        while (isGameRunning()) {
            // Calcul du temps écoulé depuis la dernière frame (deltaTimeSecond)
            long currentTime = System.currentTimeMillis();
            double deltaTimeSecond = (double) (currentTime - previousTime) / 1000.0;
            previousTime = currentTime;

            update(deltaTimeSecond);
        }
    }

    private void init() {
        StdDraw.setCanvasSize(1024, 720);
        StdDraw.setXscale(-12, 1012);
        StdDraw.setYscale(-10, 710);
        StdDraw.enableDoubleBuffering();

        map = new GameMap("./resources/maps/10-10.mtp"); // Nom de la carte, modifiable selon le fichier à charger

        // Chargement de la première vague d'ennemis depuis le fichier
        Tile caseApparition = map.getCaseApparition(); // récupère la case de départ "S"
        vagueEncours = ChargementVague.chargerVague("./resources/waves/wave1.wve", caseApparition);

        ennemiesActifs = new ArrayList<>();
    }

    private boolean isGameRunning() {
        
        return true; // TODO: Modifiable plus tard pour gérer les conditions d'arrêt
    }

    private void update(double deltaTimeSecond) {
        StdDraw.clear();
        map.draw(); // Appel la méthode draw de pour afficher la carte

        if (vagueEncours != null) {
            vagueEncours.update(deltaTimeSecond, ennemiesActifs);

            if (vagueEncours.estTerminee()) {
                vagueEncours = null; // La vague est terminée
            }
        }
        StdDraw.show();

        Iterator<Enemy> iterEnnemis = ennemiesActifs.iterator();
        while (iterEnnemis.hasNext()) {
            Enemy ennemi = iterEnnemis.next();
            ennemi.seDeplacer(deltaTimeSecond);

            if (ennemi.estMort()) {
                iterEnnemis.remove(); // Supprime les ennemis morts
            }
        }

        // Dessine les ennemis actifs
        for (Enemy ennemi : ennemiesActifs) {
            ennemi.draw();
        }

    }
}
