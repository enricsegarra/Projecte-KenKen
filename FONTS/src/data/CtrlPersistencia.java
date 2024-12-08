package data;

/**
 * Classe CtrlPersistencia s'encarrega de gestionar les operacions de persistència de jugadors, KenKens, partides i rànquings.
 */
public class CtrlPersistencia {

    /**
     * Constructor de la classe CtrlPersistencia.
     */
    public CtrlPersistencia() {
    }

    /** JUGADOR

    /**
     * Verifica si un jugador amb nom d'usuari user existeix a la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @return true si el jugador existeix, false en cas contrari.
     */
    public boolean existeixJugador(String user) {
        return JugadorsData.existeix(user);
    }

    /**
     * Afegeix un nou jugador amb usuari user i contrasenya pasword a la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @param password Contrasenya del jugador.
     */
    public void afegeixJugador(String user, String pasword) {
        JugadorsData.guardaJugador(user, pasword);
    }

    /**
     * Obté la contrasenya del jugador amb nom d'usuari user.
     * @param user Nom d'usuari del jugador.
     * @return Contrasenya del jugador.
     */
    public String getPasword(String user) {
        return JugadorsData.getPasword(user);
    }

    /**
     * Esborra el jugador amb nom d'usuari user de la "base de dades", les seves partides i els seus registres als rankings
     * @param user Nom d'usuari del jugador.
     */
    public void esborraJugador(String user) {
        JugadorsData.esborra(user);
        partidesJugadorEsborrarJugador(user);
        rankingEsborraJugador(user);
    }

    /** KENKENS

    /**
     * Obté el nombre de KenKens que hi ha a la "base de dades".
     * @return Nombre de KenKens.
     */
    public int quantsKenKens() {
        return KenKenData.quantsKenKens();
    }

    /**
     * Obté el KenKen amb ID id de la "base de dades".
     * @param id ID del KenKen.
     * @return KenKen en format String.
     */
    public String getKenKen(String id) {
        return KenKenData.getKenKen(id);
    }

    /**
     * Afegeix un KenKen a la "base de dades".
     * @param id ID del KenKen.
     * @param K KenKen en format String.
     */
    public void afegeixKenKen(String id, String K) {
        KenKenData.afegeixKenKen(id, K);
    }

    /** PARTIDES JUGADOR

    /**
     * Verifica si un jugador amb nom d'usuari user té partides a la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @return true si el jugador no té partides, false en cas contrari.
     */
    public boolean partidesJugadorEmpty(String user) {
        return PartidesData.partidesJugadorEmpty(user);
    }

    /**
     * Obté el nombre de partides del jugador amb nom d'usuari user a la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @return Nombre de partides del jugador.
     */
    public int partidesJugadorSize(String user) {
        return PartidesData.partidesJugadorSize(user);
    }

    /**
     * Obté la partida amb id de partida idp del jugador amb nom d'usuari user a la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @param idp ID de la partida.
     * @return Partida en format String.
     */
    public String partidesJugadorGetPartida(String user, String idp) {
        return PartidesData.partidesJugadorGetPartida(user, idp);
    }

    /**
     * Obté el KenKen associat a la partida amb id de partida idp del jugador amb nom d'usuari user a la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @param idp ID de la partida.
     * @return KenKen en format String.
     */
    public String partidesJugadorGetKenKen(String user, String idp) {
        return PartidesData.partidesJugadorGetKenKen(user, idp);
    }

    /**
     * Obté l'ID del KenKen associat a la partida amb id de partida idp del jugador amb nom d'usuari user a la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @param idp ID de la partida.
     * @return ID del KenKen.
     */
    public int partidesJugadorGetIdK(String user, String idp) {
        return PartidesData.partidesJugadorGetIdK(user, idp);
    }

    /**
     * Afegeix una partida a la "base de dades".
     * @param partida Partida en format String.
     */
    public void afegeixPartida(String partida) {
        PartidesData.afegeixPartida(partida);
    }

    /**
     * Esborra la partida amb id de partida idp del jugador amb nom d'usuari user de la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @param idp ID de la partida.
     */
    public void esborraPartida(String user, String idp) {
        PartidesData.esborraPartida(user, idp);
    }

    /**
     * Assigna el KenKen k a la partida amb id de partida idp del jugador amb nom d'usuari user a la "base de dades".
     * @param user Nom d'usuari del jugador.
     * @param idp ID de la partida.
     * @param k KenKen en format String.
     */
    public void partidaJugadorSetKenKen(String user, String idp, String k) {
        PartidesData.partidaJugadorSetKenKen(user, idp, k);
    }

    /**
     * Afegeix partides per al jugador amb nom d'usuari user la "base de dades".
     * @param user Nom d'usuari del jugador.
     */
    public void afegeixPartidesJugador(String user) {
        PartidesData.afegeixPartidesJugador(user);
    }

    /**
     * Esborra totes les partides del jugador amb nom d'usuari user de la "base de dades".
     * @param user Nom d'usuari del jugador.
     */
    public void partidesJugadorEsborrarJugador(String user) {
        PartidesData.esborrarJugador(user);
    }

    /** RANKING

    /**
     * Obté la mida del rànquing per a una dificultat donada a la "base de dades".
     * @param dif Nivell de dificultat.
     * @return Mida del rànquing.
     */
    public int tamanyRanking(int dif) {
        return RankingData.tamanyRanking(dif);
    }

    /**
     * Obté el rànquing per a una dificultat donada de la "base de dades".
     * @param dif Nivell de dificultat.
     * @return Rànquing en format String.
     */
    public String getRanking(int dif) {
        return RankingData.getRanking(dif);
    }

    /**
     * Actualitza el rànquing per a una dificultat donada de la "base de dades" afegint el jugador amb nom d'usuari nom i els seus punts si no hi era o modificant els seus punts si aquests son inferiors als anteriors.
     * @param dif Nivell de dificultat.
     * @param nom Nom del jugador.
     * @param punts Punts del jugador.
     */
    public void actualitzarRanking(int dif, String nom, int punts) {
        RankingData.actualitzarRanking(dif, nom, punts);
    }

    /**
     * Esborra el jugador amb nom d'usuari user dels rànquings de tots els nivells de dificultat en la "base de dades".
     * @param user Nom d'usuari del jugador.
     */
    public void rankingEsborraJugador(String user) {
        for (int i = 3; i < 9; ++i) {
            RankingData.esborrarJugador(user, i);
        }
    }
}
