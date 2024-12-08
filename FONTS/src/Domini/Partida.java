package Domini;

/**Classe Partida*/
public class Partida {
    /**KenKen de la partida*/
    private KenKen k;
    /**KenKen auxiliar de la partida. Al reprendre una partida es copia el kenken k a kAux per si després no es vol guardar la partida*/
    private KenKen kAux;
    /**KenKen amb la solució del kenken k. Només s'utilitza per kenkens generats aleatòriament, ja que la solució no es troba a persistència*/
    private KenKen solucioAleatori;
    /**Jugador que està duent a terme la partida*/
    private Jugador jugador;
    /**Enter amb l'identificador de la partida*/
    private int idPartida;
    /**Enter amb l'identificador del kenken (en cas de que en tingui, sinó -1)*/
    private int idk;
    /**Enter amb les pistes solicitades de la partida*/
    private int pistes;
    /**Enter amb una còpia de les pistes per a quan es reprèn la partida (igual que kAux)*/
    private int pistesaux;
    /**Enter amb els errors de la partida*/
    private int errors;
    /**Enter amb una còpia de els errors per a quan es reprèn la partida (igual que kAux)*/
    private int errorsaux;
    /**Booleà que identifica si la partida ha sigut finalitzada (true) o no (false)*/
    private boolean finalitzada;
    /**Timer auxiliar que es copia al reprendre una partida (igual que kAux)*/
    Timer taux;
    /**Timer de la partida*/
    Timer t;

    /** Creadora de Partida sense variables inicialitzades*/
    public Partida() {}

    /** Creadora de Partida on s’assignen totes les seves variables a partir de les passades per paràmetre
     * @param j Jugador que du a terme la partida
     * @param id Identificador de la partida
     * @param idk Identificador del KenKen de la partida (si en té)
     * @param k KenKen a assignar a la partida */
    public Partida(Jugador j, int id, int idk, KenKen k) {
        this.jugador = j;
        this.idPartida = id;
        this.k = k;
        this.idk = idk;
        kAux = new KenKen();
        solucioAleatori = new KenKen();
        pistes = 0;
        errors = 0;
        pistesaux = -1;
        errorsaux = -1;
        finalitzada = false;
        t = new Timer();
        taux = new Timer();
    }

    /** Inicia el Timer de la Partida*/
    public void iniciarPartida() {
        t.start();
    }

    /** Pausa el Timer de la Partida*/
    public void pausarPartida() {
        t.pause();
    }

    /** Resumeix el Timer de la Partida*/
    public void reanudarPartida() {
        t.resume();
    }

    /**Consultora que retorna el Jugador de la partida */
    public Jugador getJugador() {
        return jugador;
    }

    /**Modificadora que assigna un Jugador a la partida
     * @param j Jugador a assignar */
    public void setJugador(Jugador j) {
        jugador = j;
    }

    /**Consultora que retorna l'identificador de la partida */
    public int getId() {
        return idPartida;
    }

    /**Modificadora que assigna un identificador de partida a la partida
     * @param id Identificador a assignar */
    public void setId(int id) {
        idPartida = id;
    }

    /**Consultora que retorna l'identificador del KenKen */
    public int getIdk() {
        return idk;
    }

    /**Consultora que retorna el temps de la partida */
    public int getTime() {
        return t.getTimeElapsed();
    }

    /**Consultora que retorna el nombre de pistes sol·licitades de la partida */
    public int getPistes() {
        return pistes;
    }

    /**Consultora que retorna si la partida ha estat finalitzada o no */
    public boolean getEstat() {
        return finalitzada;
    }

    /**Modificadora que assigna la partida com a finalitzada */
    public void setEstat() {
        finalitzada = true;
    }

    /**Modificadora que augmenta en un els errors comesos a la partida */
    public void error() {
        ++errors;
    }

    /**Consultora que retorna els errors comesos a la partida */
    public int getErrors() {
        return errors;
    }

    /**Modificadora que augmenta en un els les pistes sol·licitades a la partida */
    public void addPista() {
        ++pistes;
    }

    /**Consultora que retorna el KenKen de la partida */
    public KenKen getKenken() {
        return k;
    }

    /**Consultora que retorna el KenKen amb la solució del KenKen de la partida (el qual s'ha generat aleatòriament) */
    public KenKen getKenkenSolAleatori() {
        return solucioAleatori;
    }

    /**Consultora que retorna la mida del KenKen amb la solució del KenKen de la partida (el qual s'ha generat aleatòriament) */
    public int consultaTamanyKSolGen() {
        return solucioAleatori.consultaTamany();
    }

    /**Modificadora que assigna un KenKen a la partida
     * @param k KenKen a assignar */
    public void setKenken(KenKen k) {
        this.k = k;
    }

    /**Modificadora que assigna un KenKen amb la solució del KenKen de la partida (el qual s'ha generat aleatòriament)
     * @param ken KenKen a assignar */
    public void setKenkenAleatoriSol(KenKen ken) {
        solucioAleatori = ken;
    }

    /** Copia el KenKen k a kAux, els errors a errorsaux, les pistes a pistesaux i el Timer t al Timer taux*/
    public void copiaKenKen() {
        kAux = new KenKen(k,true);
        errorsaux = errors;
        pistesaux = pistes;
        taux = t;
    }

    /** Copia el KenKen kAux a k, els errors a errorsaux, les pistes a pistesaux i el Timer t al Timer taux*/
    public void KenKenNoGuarda() {
        this.k = new KenKen(kAux,true);
        errors = errorsaux;
        pistes = pistesaux;
        t = taux;
    }

    /**Consultora que retorna la mida del KenKen auxiliar de la partida */
    public int consultaTamanyKaux() {
        return kAux.consultaTamany();
    }

    /** Consultora que retorna el càlcul dels punts de la partida*/
    public int getPunts() {
        return getTime() + errors*7 + pistes*10 ;
    }
 
    /**Modificadora que assigna un nombre de pistes sol·licitades a la partida 
     * @param p Nombre de pistes sol·licitades a assignar */
    public void setPistes(int p){
        pistes = p;
    }

    /**Modificadora que assigna un nombre d'errors comesos a la partida 
     * @param e Nombre d'errors comesos a assignar */
    public void setErrors(int e){
        errors = e;
    }
    /**Modificadora que assigna un Timer a la partida 
     * @param timer Timer a assignar */
    public void setTimer(Timer timer){
        t = timer;
    }

}