package Domini;

/**Classe ParNomPunts. Representa un parell nom-punts (com un pair<String,Int> de C++)*/
public class ParNomPunts {
    /**String que representa el nom del jugador*/
    private String nombre;
    /**Enter amb la puntuació obtinguda en una partida*/
    private int puntos;

    /**Creadora de la classe ParNomPunts
     * @param nombre Nom d'usuari
     * @param punts Punts obtinguts a la partida */
    public ParNomPunts(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    /**Consultora que retorna el nom d'usuari */
    public String consultarNom() {
        return nombre;
    }

    /**Consultora que retorna la puntuació */
    public int consultarPunts() {
        return puntos;
    }

    /**Modificadora que canvia la puntuació 
     * @param puntos Puntuació a assignar */
    public void modPunts(int puntos) {
        this.puntos = puntos;
    }
    
}