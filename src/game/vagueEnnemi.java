package game;

import java.util.ArrayList;
import java.util.List;

import main.Game;

public class vagueEnnemi {
    private List<Enemy> ennemis;
    private double tempsProchainEnnemi;
    private double tempsEntreEnnemis; // Temps entre chaque apparition d'ennemi

    public vagueEnnemi(List<Enemy> ennemis, double tempsEntreEnnemis) {
        this.ennemis = new ArrayList<>();
        this.tempsProchainEnnemi = 0;
        this.tempsEntreEnnemis = tempsEntreEnnemis;
    }

    public void ajouterEnnemi(Enemy ennmie) {
        ennemis.add(ennmie);
    }

    public void update(double deltaTimeSecond) {
        tempsProchainEnnemi += deltaTimeSecond;
        // Faire apparaitre les ennemis selon l'intervalle de temps
        if (tempsProchainEnnemi >= tempsEntreEnnemis && !ennemis.isEmpty()) {
            Enemy ennemi = ennemis.remove(0);
            
            //Game.addEnemyToMap(ennemi);
            tempsProchainEnnemi = 0;
        }
    }

    public boolean estTerminee() { // Vérifie si la vague est terminée
        return ennemis.isEmpty();
    }

    public List<Enemy> getEnnemis() {
        return ennemis;
    }

    public void setEnnemis(List<Enemy> ennemis) {
        this.ennemis = ennemis;
    }

    public double getTempsProchainEnnemi() {
        return tempsProchainEnnemi;
    }

    public void setTempsProchainEnnemi(double tempsProchainEnnemi) {
        this.tempsProchainEnnemi = tempsProchainEnnemi;
    }

    public double getTempsEntreEnnemis() {
        return tempsEntreEnnemis;
    }

    public void setTempsEntreEnnemis(double tempsEntreEnnemis) {
        this.tempsEntreEnnemis = tempsEntreEnnemis;
    }
}
