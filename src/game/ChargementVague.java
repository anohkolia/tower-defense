package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ChargementVague {

    public static VagueEnnemi chargerVague(String vagueFile, List<Tile> chemin) {
        VagueEnnemi vague = new VagueEnnemi(); // Création de nouvelle vague d'ennemis

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

    // Création d'un ennemi en fonction du type
    private static Enemy creerEnnemi(String typeEnnemi, List<Tile> chemin) {
        switch (typeEnnemi) {
            case "Basic":
                return new Enemy(chemin, 40, 0.1, 10, 10, 1, 2, 3);
            case "FireBasic":
                return new Enemy(chemin, 50, 2, 10, 10, 1, 2, 7); 
            case "WaterBasic":
                return new Enemy(chemin, 50, 2, 10, 10, 1, 2, 7);
            case "EarthBasic":
                return new Enemy(chemin, 50, 2, 10, 10, 1, 2, 7);
            case "AirBasic":
                return new Enemy(chemin, 50, 2, 10, 10, 1, 2, 7);
            default:
                throw new IllegalArgumentException("Type d'ennemi inconnu : " + typeEnnemi);
        }
    }
}
