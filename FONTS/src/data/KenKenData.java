package data;

import java.io.*;

/**
 * Classe KenKenData s'encarrega de la gestió de les dades dels KenKens.
 */
public class KenKenData {

    /**
     * Retorna el nombre total de KenKens guardats.
     * @return El nombre total de KenKens.
     */
    public static int quantsKenKens() {
        File directorioPrincipal = new File("src/data/kenkens");

        int totalKenKens = 0;

        File[] archivosKenKen = directorioPrincipal.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (archivosKenKen != null) {
            totalKenKens += archivosKenKen.length;
        }

        return totalKenKens;
    }

    /**
     * Afegeix un fitxer .txt a la carpeta kekens amb nom k+id i contingut info.
     * @param id L'identificador del KenKen.
     * @param info La informació del KenKen.
     */
    public static void afegeixKenKen(String id, String info) {
        String[] lineas = info.split("\n");

        File directorio = new File("src/data/kenkens");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        File archivo = new File(directorio, "k" + id + ".txt");

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(info);
        } catch (IOException e) {
            System.err.println("Error en escriure en l'arxiu del KenKen.");
            e.printStackTrace();
        }
    }

    /**
     * Retorna el contingut del fitxer .txt de la carpeta kenkens amb nom k+id.
     * @param id L'identificador del puzzle KenKen.
     * @return La informació del puzzle KenKen.
     */
    public static String getKenKen(String id) {
        File archivo = new File("src/data/kenkens/k" + id + ".txt");

        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error en llegir l'arxiu del KenKen.");
            e.printStackTrace();
        }

        return contenido.toString();
    }
}
