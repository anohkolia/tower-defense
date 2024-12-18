package main;

import game.ChargementNiveau;
import game.ChargementVague;
import game.Enemy;
import game.GameMap;
import game.Niveau;
import game.Tile;
import game.VagueEnnemi;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ui.StdDraw;

// Gestion de la boucle de jeu et initialisation de la carte.
public class Game {
    private GameMap map;
    private VagueEnnemi vagueEncours; // Vague d'ennemis actuellement en cours
    private List<Enemy> ennemiesActifs;
    private Niveau niveau; // Niveau actuel
    private List<VagueEnnemi> vagues; // Liste des vagues du niveau

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

        niveau = ChargementNiveau.chargerNiveau("./resources/levels/level1.lvl"); // Chargement du niveau depuis le fichier

        map = new GameMap("./resources/maps/" + niveau.getMapFile() + ".mtp"); //

        List<Tile> chemin = map.calculerCheminVersBase(); // Calcule le chemin

        // Chargement des vagues depuis les fichiers
        vagues = new ArrayList<>();
        for (String vagueFile : niveau.getVaguesFiles()) {
            VagueEnnemi vague = ChargementVague.chargerVague("./resources/waves/" + vagueFile + ".wve", chemin);
            vagues.add(vague);
        }

        ennemiesActifs = new ArrayList<>();
        vagueEncours = null; // Pas de vague en cours au début

        if (chemin.isEmpty()) {
            throw new IllegalStateException("Impossible de trouver un chemin entre 'S' et 'B'.");
        }
        
    }

    private boolean isGameRunning() {
        
        return true; // TODO: Modifiable plus tard pour gérer les conditions d'arrêt
    }

    private void update(double deltaTimeSecond) {
        StdDraw.clear();

        drawZones();

        map.draw(); // Appel la méthode draw de pour afficher la carte

        if (vagueEncours != null) {
            vagueEncours.update(deltaTimeSecond, ennemiesActifs);

            if (vagueEncours.estTerminee()) {
                vagueEncours = null; // La vague est terminée
            }
        }
        
        // Si aucune vague n'est en cours, on lance la prochaine vague
        if (vagueEncours == null && !vagues.isEmpty()) {
            vagueEncours = vagues.remove(0); // Récupère la première vague de la liste
        }

        // Mise à jour et dessin des ennemis actifs
        Iterator<Enemy> iterEnnemis = ennemiesActifs.iterator();
        while (iterEnnemis.hasNext()) {
            Enemy ennemi = iterEnnemis.next();
            ennemi.seDeplacer(deltaTimeSecond);
            
            if (ennemi.estMort() || ennemi.estArrive()) {
                iterEnnemis.remove(); // Supprime les ennemis morts ou arriver
            } else {
                ennemi.draw();
            }
        }

        StdDraw.show();

        /* // Dessine les ennemis actifs
        for (Enemy ennemi : ennemiesActifs) {
            ennemi.draw();
        } */

    }

    private void drawZones() {
        // Zone Rouge (Progression)
        
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.rectangle(856, 688, 144, 12);
    
        // Zone Verte (Joueur)
        
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.rectangle(856, 641, 144, 25);
    
        // Zone Bleue (Magasin)
        
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.rectangle(856, 303, 144, 303);
    }
    
}
