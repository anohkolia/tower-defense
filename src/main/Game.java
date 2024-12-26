package main;

import game.ChargementNiveau;
import game.ChargementVague;
import game.Enemy;
import game.GameMap;
import game.Niveau;
import game.Player;
import game.Tile;
import game.Tower;
import game.VagueEnnemi;
import java.awt.Font;
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
    private Player joueur;
    private List<Tower> tours; // Liste des tours placées sur la carte
    private boolean pause = false;

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

    // Méthode pour mettre en pause le jeu
    public void togglePause() {
        pause = !pause;
    }

    private void init() {
        StdDraw.setCanvasSize(1024, 720);
        StdDraw.setXscale(-12, 1012);
        StdDraw.setYscale(-10, 710);
        StdDraw.enableDoubleBuffering();

        joueur = new Player(100, 5); // Création du joueur avec 100 pièces d'or et 5 vies

        tours = new ArrayList<>();

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

            if (ennemi.estArrive()) {
                iterEnnemis.remove(); // Supprime les ennemis arrivé
                joueur.perdreVie(); // Le joueur perd une vie
            } else if (ennemi.estMort()) {
                iterEnnemis.remove(); // Supprime les ennemis mort
                joueur.gagnerArgent(ennemi.getReward()); // Le joueur gagne de l'argent
            } else {
                ennemi.draw();
            }
        }

        // Si le joueur n'a plus de vie, c'est la fin du jeu
        if (joueur.getVies() <= 0) {
            drawGameOver();
            /* System.out.println("Game Over !");
            System.exit(0); */
        }

        // Gestion des tours
        handleInput();
        if (tours != null) {
            for (Tower tour : tours) {
                tour.attaquer(ennemiesActifs);
                tour.draw();
            }
        }

        drawPlayerInfo();

        StdDraw.show();

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

    private void drawPlayerInfo() {
        StdDraw.setPenColor(StdDraw.RED);
        Font playerInfoFont = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(playerInfoFont);
        StdDraw.text(760, 641, "Vies: " + joueur.getVies());
        StdDraw.setPenColor(StdDraw.ORANGE);
        StdDraw.text(910, 641, "Argent: " + joueur.getArgent() + " Or");
    }

    private void drawGameOver() {
        StdDraw.setPenColor(StdDraw.RED);

        // Sauvegarde la police actuelle
        Font currentFont = StdDraw.getFont();

        Font gameOverFont = new Font("Arial", Font.BOLD, 50);
        StdDraw.setFont(gameOverFont);
        StdDraw.text(500, 500, "Game Over !");

        // Restaure la police précédente
        StdDraw.setFont(currentFont);
    }

    // Gestion de l'input de la souris pour placer des tours
    public void handleInput() {
        if (StdDraw.isMousePressed()) {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();

            for (int row = 0; row < map.getHeight(); row++) {
                for (int col = 0; col < map.getWidth(); col++) {
                    Tile tile = map.getTile(row, col);
                    // Si la case est constructible et non occupée
                    if (tile.getType() == 'C' && tile.isInside(mouseX, mouseY) && !tile.isOccupe()) {
                        Tower tour = new Tower(tile, 100, 10, 50); // Exemple : portée 100, dégâts 10, coût 50
                        if (joueur.depenserArgent(tour.getCout())) {
                            tours.add(tour);
                            tile.setOccupe(true);
                        } else {
                            System.out.println("Pas assez d'argent pour placer une tour !");
                        }
                        break;
                    }
                }
            }
        }
    }

}
