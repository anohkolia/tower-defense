package game;

import java.util.ArrayList;
import java.util.List;

public class GestionVagues {
    private List<VagueEnnemi> vagues;
    private int indiceVagueCourante; // Indice de la vague courante
    private double tempsEcoule; // Temps écoulé depuis le début de la vague

    public GestionVagues() {
        this.vagues = new ArrayList<>();
        this.indiceVagueCourante = 0;
        this.tempsEcoule = 0;
    }

    public void ajouterVague(VagueEnnemi vague) {
        this.vagues.add(vague);
    }

    public void update(double deltaTimeSecond, List<Enemy> ennemisActifs) {
        if (indiceVagueCourante < vagues.size()) {
            VagueEnnemi vagueCourante = vagues.get(indiceVagueCourante);
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
        return indiceVagueCourante >= vagues.size();
    }

    public List<VagueEnnemi> getVagues() {
        return vagues;
    }

    public void setVagues(List<VagueEnnemi> vagues) {
        this.vagues = vagues;
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
