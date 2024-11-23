package game;

import java.util.List;

public class Niveau {
    private String mapFile; // Fichier de la carte
    private List<String> vaguesFiles; // Liste des fichiers de vagues

    public Niveau(String mapFile, List<String> vaguesFiles) {
        this.mapFile = mapFile;
        this.vaguesFiles = vaguesFiles;
    }

    public String getMapFile() {
        return mapFile;
    }

    public void setMapFile(String mapFile) {
        this.mapFile = mapFile;
    }

    public List<String> getVaguesFiles() {
        return vaguesFiles;
    }

    public void setVaguesFiles(List<String> vaguesFiles) {
        this.vaguesFiles = vaguesFiles;
    }
}
