package game;

import java.util.ArrayList;
import java.util.List;

public class GestionVagues {
    private List<VagueEnnemi> nombreVagues;
    private int indiceVagueCourante; // Indice de la vague courante
    private double tempsEcoule; // Temps écoulé depuis le début de la vague

    public GestionVagues() {
        this.nombreVagues = new ArrayList<>();
        this.indiceVagueCourante = 0;
        this.tempsEcoule = 0;
    }

    public void ajouterVague(VagueEnnemi nombreVagues) {
        this.nombreVagues.add(nombreVagues);
    }

    public void update(double deltaTimeSecond, List<Enemy> ennemisActifs) {
        if (indiceVagueCourante < nombreVagues.size()) {
            VagueEnnemi vagueCourante = nombreVagues.get(indiceVagueCourante);
            vagueCourante.update(deltaTimeSecond, ennemisActifs);

            if (vagueCourante.estTerminee()) {
                indiceVagueCourante++;
                tempsEcoule = 0;
            } else {
                tempsEcoule += deltaTimeSecond;
            }
        }
    }

    public boolean jeuTerminee() {
        return indiceVagueCourante >= nombreVagues.size();
    }

    public List<VagueEnnemi> getNombreVagues() {
        return nombreVagues;
    }

    public void setNombreVagues(List<VagueEnnemi> nombreVagues) {
        this.nombreVagues = nombreVagues;
    }

    public int getIndiceVagueCourante() {
        return indiceVagueCourante;
    }

    public void setIndiceVagueCourante(int indiceVagueCourante) {
        this.indiceVagueCourante = indiceVagueCourante;
    }

    public double getTempsEcoule() {
        return tempsEcoule;
    }

    public void setTempsEcoule(double tempsEcoule) {
        this.tempsEcoule = tempsEcoule;
    }
}
