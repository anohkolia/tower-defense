package game;

import java.util.List;

public class Player {
    private int argent; // Argent du joueur
    private int vies; // Vies du joueur


    public Player(int argentInitial, int viesInitiales) {
        this.argent = argentInitial;
        this.vies = viesInitiales;
    }

    public int getArgent() {
        return argent;
    }

    public int getVies() {
        return vies;
    }

    public void perdreVie() {
        vies--;
    }

    public void gagnerArgent(int montant) {
        argent += montant;
    }

    public boolean depenserArgent(int montant) {
        if (argent < montant) {
            throw new IllegalArgumentException("Le joueur n'a pas assez d'argent pour effectuer cette transaction.");
        }
        argent -= montant;

        return true;
    }

    public Tower construireTour(Tile position, int coutTour, int portee, int degats) {
        // Vérifier si le joueur a assez d'argent pour construire la tour
        if (argent >= coutTour && position.getType() == 'C') {
            Tower nouvelleTour = new Tower(position, portee, degats, coutTour, 10,10);
            // tours.add(nouvelleTour);
            return nouvelleTour;
        }else if (!position.isOccupe()) {
            System.out.println("Pas assez d'argent ou case occupé !");
        }
        // return false
        return null;
    }

}
