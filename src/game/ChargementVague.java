package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ChargementVague {

    public static vagueEnnemi chargerVague(String vagueFile, List<Tile> chemin) {
        vagueEnnemi vague = new vagueEnnemi(); // Création de nouvelle vague d'ennemis

        try (BufferedReader reader = new BufferedReader(new FileReader(vagueFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Découpage de la ligne en plusieurs éléments
                String[] parts = line.split("\\|");
                double tempsApparition = Double.parseDouble(parts[0]);
                String typeEnnemi = parts[1]; // Type de l'ennemi

                // Création de l'ennemi en fonction du type
                Enemy ennemi = creerEnnemi(typeEnnemi, chemin);

                // Ajout de l'ennemi avec son temps d'apparition à la vague
                vague.ajouterEnnemi(ennemi, tempsApparition);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vague;
    }

    private static Enemy creerEnnemi(String typeEnnemi, List<Tile> chemin) {
        switch (typeEnnemi) {
            case "Basic":
                return new Enemy(chemin, 50, 100); // Vitesse de 50 et 100 points de vie
            // Ajouter d'autres types d'ennemis ici
            default:
                throw new IllegalArgumentException("Type d'ennemi inconnu : " + typeEnnemi);
        }
    }
}
