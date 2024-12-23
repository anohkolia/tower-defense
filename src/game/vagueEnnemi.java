package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class vagueEnnemi {
    private List<Enemy> ennemis;
    private List<Double> tempsApparitionEnnemis;
    private double tempsEcoule; // Temps écoulé depuis le début de la vague

    public vagueEnnemi() {
        this.ennemis = new ArrayList<>();
        this.tempsApparitionEnnemis = new ArrayList<>();
        this.tempsEcoule = 0;
    }

    public void ajouterEnnemi(Enemy ennmie,double tempsApparition) {
        this.ennemis.add(ennmie);
        this.tempsApparitionEnnemis.add(tempsApparition);
    }

    public void update(double deltaTimeSecond, List<Enemy> ennemisActifs) {
        tempsEcoule += deltaTimeSecond;

        // Parcours les ennemis pour les engendrer au bon moment
        Iterator<Double> iterTempsApparition = tempsApparitionEnnemis.iterator();
        Iterator<Enemy> iterEnnemis = ennemis.iterator();

        while (iterTempsApparition.hasNext() && iterEnnemis.hasNext()) {
            double tempsApparition = iterTempsApparition.next();
            Enemy ennemi = iterEnnemis.next();

            if (tempsEcoule >= tempsApparition) {
                ennemisActifs.add(ennemi);
                iterTempsApparition.remove(); // Supprime le temps d'apparition
                iterEnnemis.remove(); // Supprime l'ennemi de la liste
            }
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

    public List<Double> getTempsApparitionEnnemis() {
        return tempsApparitionEnnemis;
    }

    public void setTempsApparitionEnnemis(List<Double> tempsApparitionEnnemis) {
        this.tempsApparitionEnnemis = tempsApparitionEnnemis;
    }

    public double getTempsEcoule() {
        return tempsEcoule;
    }

    public void setTempsEcoule(double tempsEcoule) {
        this.tempsEcoule = tempsEcoule;
    }
}
