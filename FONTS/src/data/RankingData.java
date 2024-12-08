package data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe RankingData s'encarrega de la gestió de les dades del rànquing.
 */
public class RankingData {

    /**
     * Actualitza el fitxer JSON del ranking de dificultat dif afegint (o modificant) el nom jugador i els punts proporcionats (si aquests són menors als que hi havien).
     * @param dif La dificultat del rànquing.
     * @param nom El nom del jugador.
     * @param punts Els punts obtinguts pel jugador.
     */
    public static void actualitzarRanking(int dif, String nom, int punts) {
        File directorioPrincipal = new File("src/data/ranking");
        String FILE_PATH = directorioPrincipal + "/ranking" + dif + ".json";
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject usuario;
            if (jsonObject.containsKey(nom)) {
                usuario = (JSONObject) jsonObject.get(nom);
                int punts2 = ((Long) usuario.get("punts")).intValue();
                if (punts < punts2) {
                    usuario.put("punts", punts);
                }
            } else {
                usuario = new JSONObject();
                usuario.put("punts", punts);
                jsonObject.put(nom, usuario);
            }

            try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
                fileWriter.write(jsonObject.toJSONString());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obté el rànquing de dificultat dif del fitxer JSON correesponent amb els jugadors endreçats per puntuació descendent.
     * @param dif La dificultat del rànquing.
     * @return Una cadena de text amb el rànquing complet.
     */
    public static String getRanking(int dif) {
        File directorioPrincipal = new File("src/data/ranking");
        String FILE_PATH = directorioPrincipal + "/ranking" + dif + ".json";
        StringBuilder resultat = new StringBuilder();

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;

            List<Map.Entry<String, Integer>> usuaris = new ArrayList<>();

            for (Map.Entry<String, JSONObject> entry : (Iterable<Map.Entry<String, JSONObject>>) jsonObject.entrySet()) {
                String nom = entry.getKey();
                JSONObject usuari = entry.getValue();
                int punts = ((Long) usuari.get("punts")).intValue();
                usuaris.add(new AbstractMap.SimpleEntry<>(nom, punts));
            }

            usuaris.sort(Comparator.comparingInt(Map.Entry::getValue));

            int posicio = 1;
            for (Map.Entry<String, Integer> usuari : usuaris) {
                resultat.append(posicio).append(".").append(" ").append(usuari.getKey()).append(" ").append(usuari.getValue()).append("\n");
                ++posicio;
            }

            if (resultat.length() > 0) {
                resultat.setLength(resultat.length() - 1);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return resultat.toString();
    }

    /**
     * Retorna la mida del rànquing, és a dir, quans jugadors té el ranquing, per a una dificultat específica.
     * @param dif La dificultat del rànquing.
     * @return La mida del rànquing.
     */
    public static int tamanyRanking(int dif) {
        File directorioPrincipal = new File("src/data/ranking");
        String FILE_PATH = directorioPrincipal + "/ranking" + dif + ".json";
        int tamany = 0;

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;

            tamany = jsonObject.size();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return tamany;
    }

    /**
     * Esborra el jugador amb nom d'usuari user del rànquing per a una dificultat específica.
     * @param user El nom d'usuari del jugador.
     * @param dif La dificultat del rànquing.
     */
    public static void esborrarJugador(String user, int dif) {
        File directorioPrincipal = new File("src/data/ranking");
        String FILE_PATH = directorioPrincipal + "/ranking" + dif + ".json";

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;

            if (jsonObject.containsKey(user)) {
                jsonObject.remove(user);

                try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
                    fileWriter.write(jsonObject.toJSONString());
                }

            } 
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
