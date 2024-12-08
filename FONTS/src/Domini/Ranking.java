package Domini;
import java.util.*;

/**Classe Ranking*/
public class Ranking {
    /**String amb la dificultat de les partides, el qual identifica al Ranking*/
    private String dificultat;
    /**ArrayList de ParNomPunts que representa el rànquing*/
    private ArrayList<ParNomPunts> ranking;

    /** Creadora de Ranking que inicialitza els seus parametres
     * @param dif Dificultat de les partides que es mostraran al rànquing */
    public Ranking(String dificultat) {
        this.dificultat = dificultat;
        ranking = new ArrayList<>();
    }

    /** Entra el nom d’un usuari i els seus punts. L’afegeix al Ranking si no hi era, actualitza els seus punts si hi era, i reordena el Ranking
     * @param nombre Nom d'usuari
     * @param punts Punts obtinguts a la partida */
    public void actualitzar(String nom, int punts) {
        // Buscar si el jugador ja esta al ranking
        ParNomPunts p;
        Iterator<ParNomPunts> it = ranking.iterator();
        boolean trobat = false;
        while (it.hasNext() && !trobat) {
            p = it.next();
            if (p.consultarNom().equals(nom)) {
                trobat = true;
                // Actualitzar punts si son mes grans que els anteriors
                if (punts < p.consultarPunts()) {
                    p.modPunts(punts);
                    ordenarRanking();
                }
            }
        }

        // Si no esta al ranking
        if (!trobat) {
            ranking.add(new ParNomPunts(nom, punts));
            ordenarRanking();
        }
    }

    /** Sorter que ordena el Ranking per punts*/
    private void ordenarRanking() {
        Collections.sort(ranking, new Comparator<ParNomPunts>() {
            @Override
            public int compare(ParNomPunts p1, ParNomPunts p2) {
                return Integer.compare(p1.consultarPunts(), p2.consultarPunts());
            }
        });
    }

    /** Fa un print dels jugadors i punts del Ranking*/
    public String imprimirRanking() {
        StringBuilder r = new StringBuilder();
        int posicio = 1;
        for (ParNomPunts par : ranking) {
            r.append(posicio).append(". ").append(par.consultarNom()).append(" - ").append(par.consultarPunts()).append(" punts\n");
            posicio++;
        }
        return r.toString();
    }
    
    /**Consultora que retorna la mida (el nombre d'entrades) del rànquing */
    public int consultarTamany() {
        return ranking.size();
    }

    /**Consultora que retorna la dificultat que identifica al rànquing */
    public String consultarDificultat() {
        return dificultat;
    }

}
