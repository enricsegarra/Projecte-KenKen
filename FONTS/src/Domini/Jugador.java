package Domini;

/**Classe Jugador*/
public class Jugador {
    
    /**String que representa el nom d'usuari del jugador*/
    private String user;
    /**String que representa la contrasenya del jugador*/
    private String password;

    /** Creadora buida de Jugador.*/
    public Jugador() {
        user = null;
        password = null;
    }

    /** Creadora per paràmetres de jugador, l’inicialitza amb user = u i password = p
     * @param u Nom d'usuari a assignar
     * @param p Contrasenya a assignar */
    public Jugador(String u, String p) {
        user = u;
        password = p;
    }

    /** Valida si la contrasenya entrada és la de l’usuari
     * @param p Contrasenya a comprovar */
    public boolean comprovarContrasenya(String p) {
        return password.equals(p);
    }

    /** Consultora que retorna el nom d'usuari del Jugador */
    public String getUser() {
        return user;
    }

    /** Consultora que retorna el la contrasenya del Jugador */
    public String getPass() {
        return password;
    }

    /** Modificadora que assigna una contrasenya al Jugador 
     * @param p Contrasenya a assignar */
    public void setPass(String p) {
        password = p;
    }
}