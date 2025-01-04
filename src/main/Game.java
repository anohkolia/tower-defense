package main;

import game.ChargementNiveau;
import game.ChargementVague;
import game.Enemy;
import game.GameMap;
import game.GestionVagues;
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
    private GestionVagues gestionVagues; // Gestion des vagues

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
        this.pause = !this.pause;
    }

    private void init() {
        StdDraw.setCanvasSize(1024, 720);
        StdDraw.setXscale(-12, 1012);
        StdDraw.setYscale(-10, 710);
        StdDraw.enableDoubleBuffering();

        this.joueur = new Player(100, 10); // Création du joueur avec 100 pièces d'or et 5 vies

        tours = new ArrayList<>();

        niveau = ChargementNiveau.chargerNiveau("./resources/levels/level1.lvl"); // Chargement du niveau depuis le fichier

        map = new GameMap("./resources/maps/" + niveau.getMapFile() + ".mtp"); //

        List<Tile> chemin = map.calculerCheminVersBase(); // Calcule le chemin

        // Chargement des vagues depuis les fichiers
        vagues = new ArrayList<>();
        for (String vagueFile : niveau.getVaguesFiles()) {
            Tile caseApparition = map.getCaseDepart();
            Tile caseArrivee = map.getCaseArrivee();
            VagueEnnemi vague = ChargementVague.chargerVague("./resources/waves/" + vagueFile + ".wve", chemin);
            vagues.add(vague);
        }
        ennemiesActifs = new ArrayList<>();
        gestionVagues = new GestionVagues(); // Initialisation de gestionVagues
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

        gestionVagues.update(deltaTimeSecond, ennemiesActifs);

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
            }
        }
        
        for (Enemy ennemi : ennemiesActifs) {
            ennemi.draw();
            ennemi.barreDeVie();
        }
        
        // Si le joueur n'a plus de vie, c'est la fin du jeu
        if (joueur.getVies() <= 0) {
            drawGameOver();
            //System.exit(0);
        }

        // Gestion des tours
        handleInput();
        if (!getTours().isEmpty()) {
            for (Tower tour : getTours()) {
                //tour.barreDeVie();
                
                if (tour.estDetruite()) {
                    System.out.println("Tour détruite !");
                }else{
                    tour.update(deltaTimeSecond, ennemiesActifs);
                    tour.draw();
                    if(tour.getPosition().isOccupe() == false){

                        tour.getPosition().setOccupe(true); // Marquer la case comme occupée
                        joueur.depenserArgent(tour.getCout()); // Dépenser l'argent pour construire la tour
                    }
                }
               
            }
        }


        for(Enemy ennemi : ennemiesActifs) {
            ennemi.update(deltaTimeSecond, tours);
        }

        drawPlayerInfo();
        afficherStatistiques(1, gestionVagues.getIndiceVagueCourante() + 1, vagues.size() + gestionVagues.getNombreVagues().size());

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

    // info: Affiche le level actuel et le nombre de vagues restantes
    private void afficherStatistiques(int level, int vagueCourante, int nombreVagues) {
        StdDraw.setPenColor(StdDraw.BLACK);
        Font statistiquesFont = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(statistiquesFont);
        StdDraw.text(760, 688, "Level: " + level + "/4");
        StdDraw.text(910, 688, "Vague: " + vagueCourante + "/" + nombreVagues);
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
            for (Tile[] row : map.getGrid()) {
                for (Tile tile : row) {
                    if (tile.isInside(mouseX, mouseY)) {
                        Tower tour = joueur.construireTour(tile, 10, 10, 10);
                        
                        if (tour != null && tour.getPosition().isOccupe() == false ) {
                            this.tours.add(tour);
                            System.out.println("Tour placée ! ");
                        }
                    
                        // break;
                    }
                }
            }
        }
    }
    
    public List<Tower> getTours() {
        return this.tours;
    }
}
