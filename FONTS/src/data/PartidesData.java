package data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.Set;

/**
 * La classe PartidesData gestiona les dades de les partides dels usuaris.
 */
public class PartidesData {
    private static final String FILE_PATH = "src/data/partides.json";

    /**
     * Verifica si el jugador amb usuari user té partides al fitxer JSON partides .
     * @param user El nom de l'usuari.
     * @return Cert si les partides estan buides o no existeixen; fals altrament.
     */
    public static boolean partidesJugadorEmpty(String user) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject usuario = (JSONObject) jsonObject.get(user);
            return usuario == null || usuario.isEmpty();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return true;
        }
    }
    
    /**
     * Retorna el nombre de partides al fitxer JSON partides del jugador amb nom d'usuari user.
     * @param user El nom de l'usuari.
     * @return El nombre de partides de l'usuari.
     */
    public static int partidesJugadorSize(String user) {
        int partidas = 0;

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject usuario = (JSONObject) jsonObject.get(user);

            if (usuario != null) {
                partidas = usuario.size();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return partidas;
    }

    /**
     * Obté la informació del KenKen associat a la partida amb identificador idp del jugador amb usuari user del fitxer JSON partides.
     * @param user El nom de l'usuari.
     * @param idp L'identificador de la partida.
     * @return La informació del KenKen de la partida en format de cadena de text.
     */
    public static String partidesJugadorGetKenKen(String user, String idp) {
        String kenKenString = null;

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject usuario = (JSONObject) jsonObject.get(user);
            if (usuario != null) {
                JSONObject partida = (JSONObject) usuario.get(idp);
                if (partida != null) {
                    Set<String> keys = partida.keySet();
                    for (String key : keys) {
                        if (key.startsWith("k")) {
                            kenKenString = (String) partida.get(key);
                            break;
                        }
                    }
                }
            }
        
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return kenKenString;
    }

    /**
     * Obté l'identificador del KenKen associat a la partida amb identificador idp del jugador amb usuari user del fitxer JSON partides.
     * @param user El nom de l'usuari.
     * @param idp L'identificador de la partida.
     * @return L'identificador del KenKen.
     */
    public static int partidesJugadorGetIdK(String user, String idp) {
        int kenKenId = -1;

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject usuario = (JSONObject) jsonObject.get(user);
            if (usuario != null) {
                JSONObject partida = (JSONObject) usuario.get(idp);
                if (partida != null) {
                    Set<String> keys = partida.keySet();
                    for (String key : keys) {
                        if (partida.get(key) instanceof String) {
                            kenKenId = Integer.parseInt(key.substring(1, key.length()));
                            break;
                        }
                    }
                }
            }
            
        } catch (IOException | ParseException | NumberFormatException e) {
            e.printStackTrace();
        }

        return kenKenId;
    }

    /**
     * Obté la informació de la partida amb identificador idp del jugador amb usuari user del fitxer JSON partides.
     * @param user El nom de l'usuari.
     * @param idp L'identificador de la partida.
     * @return La informació de la partida en format de cadena de text.
     */
    public static String partidesJugadorGetPartida(String user, String idp) {
        StringBuilder partidaString = new StringBuilder();

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));

            JSONObject jsonObject = (JSONObject) obj;

            JSONObject usuario = (JSONObject) jsonObject.get(user);
            if (usuario != null) {
                JSONObject partida = (JSONObject) usuario.get(idp);
                if (partida != null) {
                    partidaString.append(user).append(" ").append(idp).append(" ");
                    Set<String> keys = partida.keySet();
                    String idk = ".";
                    String kenken = ".";
                    String aux = ".";
                    for (String key : keys) {
                        if (key.startsWith("k")) {
                            String idKenKen = key;
                            idk = idKenKen.substring(1, idKenKen.length());
                            kenken = (String) partida.get(idKenKen);

                        } else if (key.startsWith("a")) {
                            if (partida.get("aux").equals("res")) {
                                aux = "-1";
                            } else {
                                aux = (String) partida.get(key);
                            }
                        }
                    }

                    partidaString.append(idk).append(" ").append(kenken).append(" ").append(aux).append(" ");
                    partidaString.append(partida.get("pistes")).append(" ");
                    partidaString.append(partida.get("errors")).append(" ");
                    partidaString.append(partida.get("finalitzada")).append(" ");
                    partidaString.append(partida.get("timer"));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return partidaString.toString();
    }

    /**
     * Afegeix una nova partida amb la informació de partidaString al fitxer JSON partides.
     * @param partidaString La informació de la partida en format de cadena de text.
     */
    public static void afegeixPartida(String partidaString) {
        String[] partes = partidaString.trim().split(" ");
        String usuario = partes[0];
    
        String idPartida = partes[1];
        String idKenKen = "k"+partes[2];
        int t = Integer.parseInt(partes[3]);
        int op = Integer.parseInt(partes[4]);
        
        String kenKen;
        String kenKenaux;
        if (partes[partes.length-5]== "-1"){
            StringBuilder kenKenBuilder = new StringBuilder();
            kenKenBuilder.append(t+" "+op+" "); 
            for (int i = 5; i < partes.length - 5; i++) {
                kenKenBuilder.append(partes[i]).append(" ");
            }
            kenKen = kenKenBuilder.toString().trim();
            kenKenaux = "res";
        }
        else {
            int tamanyKenken = 0;
            for(int i = 0; i < op; ++i){
                int ncas = Integer.parseInt(partes[4+tamanyKenken+3]);
                tamanyKenken += ncas*3+3;
            }

            StringBuilder kenKenBuilder = new StringBuilder();
            kenKenBuilder.append(t+" "+op+" "); 
            for (int i = 5; i < 5+tamanyKenken; i++) {
                kenKenBuilder.append(partes[i]).append(" ");
            }
            kenKen = kenKenBuilder.toString().trim();

            int t2 = Integer.parseInt(partes[5+tamanyKenken]);
            int op2 = Integer.parseInt(partes[5+tamanyKenken+1]);

            StringBuilder kenKenBuilder2 = new StringBuilder();
            kenKenBuilder2.append(t2+" "+op2+" "); 
            for (int i = 5+tamanyKenken+2; i < partes.length - 4; i++) {
                kenKenBuilder2.append(partes[i]).append(" ");
            }
            kenKenaux = kenKenBuilder2.toString().trim();

        }
        
        
        int pistes = Integer.parseInt(partes[partes.length - 4]);
        int errors = Integer.parseInt(partes[partes.length - 3]);
        int finalitzada = Integer.parseInt(partes[partes.length - 2]);
        int timer = Integer.parseInt(partes[partes.length - 1]);

         try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;

            
            JSONObject usuarioObject = (JSONObject) jsonObject.getOrDefault(usuario, new JSONObject());
            JSONObject partidaUsuario = (JSONObject) usuarioObject.getOrDefault(idPartida, new JSONObject());


            partidaUsuario.put(idKenKen, kenKen);
            partidaUsuario.put("aux", kenKenaux);
            partidaUsuario.put("pistes", pistes);
            partidaUsuario.put("errors", errors);
            partidaUsuario.put("finalitzada", finalitzada);
            partidaUsuario.put("timer", timer);


            usuarioObject.put(idPartida, partidaUsuario);
            jsonObject.put(usuario, usuarioObject);
            
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            String jsonFormatted = jsonObject.toJSONString();
            fileWriter.write(jsonFormatted);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina la partida amb identificador idpdel jugador amb usuari user del fitxer JSON partides.
     * @param user El nom de l'usuari.
     * @param idp L'identificador de la partida.
     */
    public static void esborraPartida(String user, String idp) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject usuario = (JSONObject) jsonObject.get(user);
            if (usuario != null) {
                usuario.remove(idp);
                int idPartidaAEliminar = Integer.parseInt(idp);
                JSONObject nuevasPartidas = new JSONObject();
                for (Object key : usuario.keySet()) {
                    String clave = (String) key;
                    int idPartidaActual = Integer.parseInt(clave);

                   
                    if (idPartidaActual > idPartidaAEliminar) {
                        nuevasPartidas.put(String.valueOf(idPartidaActual - 1), usuario.get(clave));
                    } else {
                        nuevasPartidas.put(clave, usuario.get(clave));
                    }
                }
  
                jsonObject.put(user, nuevasPartidas);
                
                FileWriter fileWriter = new FileWriter(FILE_PATH);
                fileWriter.write(jsonObject.toJSONString());
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualitza la informació del KenKen associat a la partida amb identificador idp del jugador amb usuari user del fitxer JSON partides, amb l'informació de l'String k.
     * @param user El nom de l'usuari.
     * @param idp L'identificador de la partida.
     * @param k La nova informació del KenKen en format String.
     */
    public static void partidaJugadorSetKenKen(String user, String idp, String k) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;
    
            JSONObject usuario = (JSONObject) jsonObject.get(user);
    
            if (usuario != null) {
                JSONObject partida = (JSONObject) usuario.get(idp);
    
                if (partida != null) {
                    for (Object key : partida.keySet()) {
                        String clave = (String) key;
                        if (clave.startsWith("k")) { 
                            String nuevoKenKen = convertirFormatoKenKen(k);
                            
                            partida.put(clave, nuevoKenKen);
    
                            FileWriter fileWriter = new FileWriter(FILE_PATH);
                            fileWriter.write(jsonObject.toJSONString());
                            fileWriter.flush();
                            fileWriter.close();
    
                            return; 
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converteix el format de KenKen per a emmagatzemar-lo en el fitxer JSON partides.
     * @param kenKenString La informació del KenKen en format de cadena de text.
     * @return El KenKen en el format requerit per l'emmagatzematge.
     */
    public static String convertirFormatoKenKen(String kenKenString) {
        String[] filas = kenKenString.split("\n");
        
        StringBuilder nuevoKenKen = new StringBuilder();
        
        nuevoKenKen.append(filas[0].trim());
        
        for (int i = 1; i < filas.length; i++) {
            String[] elementos = filas[i].trim().split(" ");
            
            
                nuevoKenKen.append(" ");
                nuevoKenKen.append(elementos[0]).append(" ");
                nuevoKenKen.append(elementos[1]).append(" ");
                nuevoKenKen.append(elementos[2]);

                for (int k = 0; k < Integer.parseInt(elementos[2]); k++) {
                    nuevoKenKen.append(" ");
                    nuevoKenKen.append(elementos[3+k*2]).append(" ");
                    nuevoKenKen.append(elementos[4+k*2]).append(" ");
                    nuevoKenKen.append("(").append(0).append(")"); 
                }
            
        }
        
        return nuevoKenKen.toString().trim();
    }

    /**
     * Afegeix l'objecte del jugador amb nom d'usuari user al fitxer JSON partides amb les partides buides.
     * @param user El nom de l'usuari.
     */
    public static void afegeixPartidesJugador(String user) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;
    
            if (jsonObject.containsKey(user)) {
                return;
            }
    
            JSONObject partidesUsuario = new JSONObject();
    
            jsonObject.put(user, partidesUsuario);
    
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Elimina el jugador amb usuari user i totes les seves dades del fitxer JSON partides.
     * @param user El nom de l'usuari.
     */
    public static void esborrarJugador(String user) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(FILE_PATH));
            JSONObject jsonObject = (JSONObject) obj;

            if (jsonObject.containsKey(user)) {
                jsonObject.remove(user);
                try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
                    fileWriter.write(jsonObject.toJSONString());
                    fileWriter.flush();
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
