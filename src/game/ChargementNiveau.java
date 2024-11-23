package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChargementNiveau {
    public static Niveau chargerNiveau(String niveauFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(niveauFile))) {
            // Lire la première ligne pour obtenir la carte du niveau
            String mapFile = reader.readLine();

            // Lire les lignes suivantes pour obtenir les vagues
            List<String> vaguesFiles = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                vaguesFiles.add(line.trim());
            }

            return new Niveau(mapFile, vaguesFiles);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Erreur lors de la lecture du fichier de niveau : " + niveauFile);
        }
    }
}
