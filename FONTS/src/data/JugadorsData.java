package data;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe JugadorsData s'encarrega de la gestió de les dades dels jugadors.
 */
public class JugadorsData {
    private static final String FILE_PATH = "src/data/jugadors.json";
    
    /**
     * Guarda un nou jugador amb nom d'usuari username y contrasenya paswrod al fitxer JSON jugadors.
     * @param username Nom d'usuari del jugador.
     * @param password Contrasenya del jugador.
     */
    public static void guardaJugador(String username, String password) {
        try {
            if(existeix(username))  {}//System.out.println("El jugador ja existeix.");
            else{
                List<String[]> jugadores = carregarJugadors();
                String[] nouJugador = {username, password};
                jugadores.add(nouJugador);
                
                FileWriter file = new FileWriter(FILE_PATH);
                JSONArray jsonArray = new JSONArray();
                for (String[] jugador : jugadores) {
                    JSONArray jugadorArray = new JSONArray();
                    jugadorArray.add(jugador[0]); 
                    jugadorArray.add(jugador[1]);
                    jsonArray.add(jugadorArray);
                }
                file.write(jsonArray.toJSONString());
                file.flush();
                file.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Esborra el jugador amb nom d'usuari username del fitxer JSON jugadors.
     * @param username Nom d'usuari del jugador.
     */
    public static void esborra(String username) {
        try {
            List<String[]> jugadores = carregarJugadors();
            boolean jugadorTrobat = false;
            for (int i = 0; i < jugadores.size(); i++) {
                String[] jugador = jugadores.get(i);
                if (jugador[0].equals(username)) {
                    jugadores.remove(i);
                    jugadorTrobat = true;
                    break;
                }
            }

            if (!jugadorTrobat) {
                //System.out.println("El jugador no existeix.");
                return;
            }

            FileWriter file = new FileWriter(FILE_PATH);
            JSONArray jsonArray = new JSONArray();
            for (String[] jugador : jugadores) {
                JSONArray jugadorArray = new JSONArray();
                jugadorArray.add(jugador[0]); 
                jugadorArray.add(jugador[1]); 
                jsonArray.add(jugadorArray);
            }
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

            //System.out.println("El jugador '" + username + "' ha estat eliminat correctament.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Consulta un jugador.
     * @param username Nom d'usuari del jugador.
     */
    public static void consultarJugador(String username) {
        try {
            List<String[]> jugadores = carregarJugadors();

            for (String[] jugador : jugadores) {
                if (jugador[0].equals(username)) {
                    //System.out.println("Username: " + jugador[0] + ", Password: " + jugador[1]);
                    return;
                }
            }
            //System.out.println("El jugador no existeix.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega i retorna en una llista els jugadors des del fitxer JSON jugadors.
     * @return Llista de jugadors.
     * @throws IOException Si ocorre un error en llegir el fitxer.
     */
    private static List<String[]> carregarJugadors() throws IOException {
        List<String[]> jugadores = new ArrayList<>();
        JSONParser parser = new JSONParser();
    
        try {
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONArray jsonArray = (JSONArray) obj;
            for (Object o : jsonArray) {
                JSONArray jugadorArray = (JSONArray) o;
                String username = (String) jugadorArray.get(0);
                String password = (String) jugadorArray.get(1);
                String[] jugador = {username, password};
                jugadores.add(jugador);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
        return jugadores;
    }

    /**
     * Verifica si el jugador amb usuari username existeix al fitxer JSON jugadors.
     * @param username Nom d'usuari del jugador.
     * @return true si el jugador existeix, false en cas contrari.
     */
    public static boolean existeix(String username) {
        try {
            List<String[]> jugadores = carregarJugadors();
    
            for (String[] jugador : jugadores) {
                if (jugador[0].equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obté la contrasenya del jugador amb usuari username.
     * @param username Nom d'usuari del jugador.
     * @return Contrasenya del jugador o un missatge d'error si el jugador no existeix o si ocorre un error.
     */
    public static String getPasword(String username) {
        try {
            List<String[]> jugadores = carregarJugadors();

            for (String[] jugador : jugadores) {
                if (jugador[0].equals(username)) {
                    return jugador[1];
                }
            }
            return "El jugador no existeix.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al carregar els jugadors.";
        }
    }
}
