package game;

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

}
