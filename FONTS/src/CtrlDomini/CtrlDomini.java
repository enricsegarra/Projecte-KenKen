package CtrlDomini;
import Domini.*;
import Domini.Timer;
import data.CtrlPersistencia;

import java.util.*;

/**Classe del controlador de domini*/
public class CtrlDomini {
    /**Controlador de persistència*/
    private CtrlPersistencia CP;
    /**Controlador dels kenkens*/
    private CtrlKenKen CK;
    /**Jugador actual amb el qual s'ha iniciat sessió. Fins que no s'inicia sessió amb un altre usuari no es canvia*/
    private Jugador jugadorActual;
    /**Partida actual, la qual s'està jugant. Fins que no es juga una altra partida no es canvia*/
    private Partida partidaActual;
    
    /** Creadora del Controlador.*/
    public CtrlDomini() {
        CK = new CtrlKenKen();
        CP = new CtrlPersistencia();
    }

    //JUGADOR

    /** Comprova l’existència d’un jugador amb username user
     * @param user Nom d'usuari */
    public boolean existeixJugador(String user){
        return CP.existeixJugador(user);
    }

    /** Afegeix el jugador J a la capa persistència
     * @param user Nom d'usuari del jugador a afegir a la base de dades
     * @param J Jugador a afegir a la base de dades */
    public void afegeixJugador(String user, Jugador J) {
        String pasword = J.getPass();
        CP.afegeixJugador(user, pasword);
    }

    /** Afegeix l'usuari de nom user a la persistència de Partides de Jugador
     * @param user Nom d'usuari */
    public void afegeixPartidesJugador(String user){
        CP.afegeixPartidesJugador(user);
    }

    /** Retorna el jugador amb nom user des de la capa de persistència
     * @param user Nom d'usuari */
    public Jugador getJugador(String user){
        String pasword = CP.getPasword(user);
        Jugador J = new Jugador(user, pasword);
        return J;
    }

    /** Esborra de persistència el jugador de nom user
     * @param user Nom d'usuari */
    public void esborraJugador(String user){
       CP.esborraJugador(user);
    }

    /** Retorna el nombre de partides que té el jugador actual*/
    public int partidesJugadorSize(){
        return CP.partidesJugadorSize(jugadorActual.getUser());
    }

    //KENKENS

    /** Retorna el nombre de KenKens que hi ha a guardats a persistència*/
    public int quantsKenKens(){
        return CP.quantsKenKens();
    }

    /** Retorna un objecte KenKen a partir d’un KenKen guardat a persistència identificat per id
     * @param id Identificador del Kenken */
    public KenKen getKenKen(int id){
        String idk = String.valueOf(id);
        String k = CP.getKenKen(idk);
        KenKen nk= convertirFormatoKenKen(k);
        return nk;
    }

    //PARTIDES JUGADOR

    /** Retorna si el jugador d’username user té cap partida guardada
     * @param user Nom d'usuari */
    public boolean partidesJugadorEmpty(String user){
        return CP.partidesJugadorEmpty(user);
    }

    /** Retorna el nombre de partides guardades que té el jugador d’username user
     * @param user Nom d'usuari */
    public int partidesJugadorSize(String user){
        return CP.partidesJugadorSize(user);
    }

    /** Retorna un objecte partida de persistència a partir de la partida guardada identificada per idp del jugador identificat per user
     * @param user Nom d'usuari 
     * @param idp Identificador de la partida */
    public Partida partidesJugadorGetPartida(String user, int idp){
        String idPartida = String.valueOf(idp);
        String part = CP.partidesJugadorGetPartida(user, idPartida);
        Partida p = convertirFormatoPartida(part);
        return p;
    }

    /** Retorna un objecte de KenKen de persistència de la partida guardada identificada per idp, del jugador identificat per user
     * @param user Nom d'usuari 
     * @param idp Identificador de la partida */
    public KenKen partidesJugadorGetKenKen(String user, int idp){
        String idPartida = String.valueOf(idp);
        String k = CP.partidesJugadorGetKenKen(user, idPartida);
        KenKen nk = convertirFormatoKenKenP(k);
        return nk;
    }

    /** Retorna el id del KenKen de la partida guardada identificada per idp, del jugador identificat per user
     * @param user Nom d'usuari 
     * @param idp Identificador de la partida */
    public int partidesJugadorGetIdK(String user, int idp){
        String idPartida = String.valueOf(idp);
        return CP.partidesJugadorGetIdK(user, idPartida);
    }

    /** Retorna la informació del Ranking actual de la dificultat indicada per dif
     * @param user Nom d'usuari 
     * @param idp Identificador de la partida */
    public String mostrar_ranking(String dif) throws Exception {
        String r;
        String[] info = dif.split("x");
        int ndif = Integer.parseInt(info[0]);
        r = CP.getRanking(ndif);
        return r;
    }

    /** Actualitza el Ranking de dificultat dif amb el user del jugador nom, i els punts passats per paràmetre
     * @param dif Dificultat que identifica el rànquing
     * @param nom Nom d'usuari
     * @param punts Punts obtinguts a la partida */
    public void actualitzar_ranking(int dif, String nom, int punts) {
        CP.actualitzarRanking(dif, nom, punts);
    }

    /** Retorna un objecte de KenKen creat a partir de l’ArrayList d’enters, passat com a paràmetre
     * @param cap ArrayList d'ArrayLists d'enters amb el format necesari per a generar un KenKen a partir d'ell*/
    public KenKen Crea_kenken(ArrayList<ArrayList<Integer> > cap) throws Exception{
        KenKen K = CK.Crea_kenken(cap);
        return K;
    }

    /** Genera un KenKen de forma aleatoria amb el tamany indicat per paràmetre. Retorna el tamany actual de la llista de KenKens
     * @param tam Mida del KenKen a generar */
    public int generaAleatori(int tam) {
        KenKen K = CK.generaAleatori(tam);
        partidaActual.setKenken(K);
        return CP.quantsKenKens() - 1;
    }

    /** Retorna si la posició x y, amb valor v, és vàlida per al KenKen de la partida identificada per id, de les partides del jugador actual
     * @param x Fila de la casella on introduir el valor
     * @param y Columna de la casella on introduir el valor
     * @param v Valor a introduir a la casella 
     * @param id Identificador de partida*/
    public boolean introdueixValor(int x, int y, int v, int id) {
        KenKen K = partidaActual.getKenken();
        if (K.PosicioValida(x, y, v)) return true;
        return false;
    }

    /** Esborra el valor de la posició indicada per x, y, del KenKen identificat per id, de les partides del jugador actual
     * @param x Fila de la casella on esborrar el valor
     * @param y Columna de la casella on esborrar el valor
     * @param id Identificador de partida*/
    public void BorrarValor(int x, int y, int id) {
        KenKen K = partidaActual.getKenken();
        if (K.consultaValorCasella(x,y) != 0) K.setValor(x, y, 0);
    }

    /** Valida l’objecte de KenKen passat com a paràmetre. Si no és vàlid saltarà una excepció
     * @param K KenKen a validar */
    public void Valida_kenken(KenKen K) throws Exception {
        CK.validaKenKen(K,0,0);
        if (!K.consultaVerified()) {
            myException me = new myException("Error en la verificacio");
            throw me;
        }
    }

    /** Inicia sessió pels paràmetres entrats. Si les credencials són vàlides, el jugador actual passa a ser el creat pels paràmetres (que serà una còpia d’un existent)
     * @param user Nom d'usuari 
     * @param pass Contrasenya */
    public int iniciarSessio(String user, String pass) {
        if (!existeixJugador(user)) {
            return 1;
        }
        else {
            Jugador dummy = getJugador(user);
            String passCheck = dummy.getPass();
            if (!pass.equals(passCheck)) {
                return 2;
            }
            else {
                jugadorActual = dummy;
                return 0;
            }
        }
    }

    /** Si el perfil de jugador indicat pels paràmetres existeix, s’esborra
     * @param user Nom d'usuari 
     * @param pass Contrasenya */
    public int ConsultaBorraPerfil(String user, String pass) {
        if (!existeixJugador(user)) {
            return 1;
        }
        else {
            Jugador dummy = getJugador(user);
            String passCheck = dummy.getPass();
            if (!pass.equals(passCheck)) {
                return 2;
            }
            else {
                return 0;
            }
        }
    }

    /** Retorna el valor de la casella indicada per x i y, del KenKen de la partida identificada per idp de les partides del jugador actual
     * @param x Fila de la casella
     * @param y Columna de la casella
     * @param idp Identificador de partida*/
    public int consultaValor(int x, int y, int idp) {
        KenKen K = partidaActual.getKenken();
        return K.consultaValorCasella(x,y);
    }

    /** Retorna quantes zones té el KenKen de la partida indicada per idp del jugador actual
     * @param idp Identificador de partida */
    public int consultaTamanyZones(int idp) {
        KenKen K = partidaActual.getKenken();
        return K.consultaTamanyZones();
    }

    /** Retorna l’identificador de l’operació de la zona identificada per idz, del KenKen de la partida identificada per idp, del jugador actual
     * @param idz Identificador de la zona
     * @param idp Identificador de partida */
    public int consultaOperacioZona(int idz, int idp) {
        KenKen K = partidaActual.getKenken();
        Zona z = K.consultaZona(idz);
        return z.consultaOperacio();
    }

    /** Retorna el resultat de la zona identificada per idz, del KenKen de la partida identificada per idp, del jugador actual
     * @param idz Identificador de la zona
     * @param idp Identificador de partida */
    public int consultaResultatZona(int idz, int idp) {
        KenKen K = partidaActual.getKenken();
        Zona z = K.consultaZona(idz);
        return z.consultaTotal();
    }

    /** Retorna l’identificador de la zona de la casella x, y, del KenKen de la partida identificada per idp, del jugador actual
     * @param x Fila de la casella
     * @param y Columna de la casella
     * @param idp Identificador de partida */
    public int consultaIdz(int x, int y, int idp) {
        KenKen K = partidaActual.getKenken();
        return K.consultaCasellaZonaId(x,y);
    }

    /** Retorna el valor de la casella x, y, de la solució del KenKen de la partida identificada per idp, del jugador actual
     * @param x Fila de la casella
     * @param y Columna de la casella
     * @param idp Identificador de partida */
    public int consultaValorSolucio(int x, int y, int idp) {
        int idk = partidaActual.getIdk();
        KenKen K = getKenKen(idk);
        return K.consultaValorCasella(x, y);
    }

    /** Retorna el valor de la casella x, y, de la solució del KenKen generat aleatòriament de la partida actual
     * @param x Fila de la casella
     * @param y Columna de la casella
     * @param idp Identificador de partida */
    public int consultaValorSolucioGen(int x, int y, int idp) {
        KenKen K = partidaActual.getKenkenSolAleatori();
        return K.consultaValorCasella(x, y);
    }

    /** Retorna el tamany de la solució del KenKen generat aleatòriament de la partida actual*/
    public int consultaTamanyKSolGen() {
        return partidaActual.consultaTamanyKSolGen();
    }

    /** Si no existeix un usuari amb les credencials indicades per paràmetre, es crea i guarda
     * @param user Nom d'usuari 
     * @param pass Contrasenya */
    public boolean creaPerfil(String user, String pass) {
        if (existeixJugador(user)) {
            return false;
        }
        else {
            Jugador J = new Jugador(user,pass);
            afegeixJugador(user, J);
            afegeixPartidesJugador(user);
            return true;
        }
    }

    /** Retorna el tamany del KenKen de la partida identificada per idp, del jugador actual
     * @param idp Identificador de partida */
    public int consultarTamany(int idp) {
        return partidaActual.getKenken().consultaTamany();
    }

    /** Inicia una partida pel jugador actual, amb el KenKen identificat per id. Retorna el idp
     * @param idp Identificador de partida */
    public int iniciarPartida(int id) {
        int idp = CP.partidesJugadorSize(jugadorActual.getUser());
        KenKen ken = new KenKen(getKenKen(id),false);
        partidaActual = new Partida(jugadorActual, idp, id, ken);
        return idp;
    }

    /** Inicia una partida pel jugador actual, amb el KenKen generat aleatoriament de tamany tam. Retorna el identificador de la
     * @param tam Mida del KenKen generat aleatòriament */
    public int iniciarPartida2(int tam) {
        int idp = CP.partidesJugadorSize(jugadorActual.getUser());
        KenKen ken = CK.generaAleatori(tam);
        KenKen k = new KenKen(ken,false);
        partidaActual = new Partida(jugadorActual, idp, -1, k);
        partidaActual.setKenkenAleatoriSol(ken);
        return idp;
    }

    /** Pausa la partida identificada per idp passat per paràmetre i la guarda en el seu estat actual en la capa de persistència com a partida del jugador actual
     * @param idp Identificador de partida */
    public void guardarPartida(int idp) {
        partidaActual.pausarPartida();
        afegeixPartida(jugadorActual.getUser(), idp, partidaActual);
    }

    /** Funció que reprèn la partida (guardant les pistes, els errors, el timer i el kenken als atributs auxiliar de la partida)
     * @param idp Identificador de partida */
    public void copiaKenKenPartida(int idp) {
        partidaActual = partidesJugadorGetPartida(jugadorActual.getUser(), idp);
        partidaActual.copiaKenKen();
        partidaActual.reanudarPartida();
    }

    /** Es reinicia la partida actual tal com estava abans de reprendre-la*/
    public void KenKenNoGuarda() {
        partidaActual.pausarPartida();
        partidaActual.KenKenNoGuarda();
    }

    /** Retorna el tamany del KenKen auxiliar de la partida*/
    public int consultaTamanyKaux() {
        return partidaActual.consultaTamanyKaux();
    }

    /** Inicia el timer de la partida actual*/
    public void iniciarTimer() {
        partidaActual.iniciarPartida();
    }

    /** Estableix com a finalitzada la partida actual. Si b és true s’actualitza el ranking de la mida del KenKen de la partida
     * @param b Booleà que indica si s'ha d'actualitzar el rànquing */
    public int finalitzarPartida(boolean b) {
        partidaActual.setEstat();
        int punts = partidaActual.getPunts();
        int tam = partidaActual.getKenken().consultaTamany();
        if (b) actualitzar_ranking(tam,jugadorActual.getUser(),punts);
        return punts;
    }

    /** Esborra la partida identificada per idp del jugador actual
     * @param idp Identificador de partida */
    public void borrarPartida(int idp) {
        esborraPartida(jugadorActual.getUser(), idp);
    }

    /** Afegeix un error al contador de la partida actual del jugador actual*/
    public void sumaError() {
        partidaActual.error();
    }

    /** Afegeix una pista al contador de la partida actual del jugador actual*/
    public void sumaPista() {
        partidaActual.addPista();
    }

    /** Retorna un possible valor per a la casella amb posició (x,y), tal que sigui vàlid actualment i a futur per a la zona
     * @param x Fila de la casella
     * @param y Columna de la casella */
    public int consultaPista(int x, int y) {
        KenKen K = partidaActual.getKenken();
        return CK.consultaPista(K, x, y);
    }

    /** Consulta si la casella de coordenades x, y, del KenKen de la partida actual és predeterminada, és a dir té valors inicialitzats
     * @param x Fila de la casella
     * @param y Columna de la casella */
    public boolean consultaPre(int x, int y) {
        return partidaActual.getKenken().consultaCasella(x,y).getPre();
    }

    /** Crea un objecte KenKen a partir del string s passat per paràmetre. Depenent del booleà b es guarda o no
     * @param s String del que a partir del seu contingut es crea un KenKen
     * @param b Booleà que indica si el KenKen s'ha de guardar o no */
    public void creaKenKen(String s, boolean b) throws Exception {

        ArrayList<ArrayList<Integer> > cap = new ArrayList<>();
        String[] lineas = s.split("\n");

        String[] nums = lineas[0].split(" ");
        ArrayList<Integer> aux = new ArrayList<>();
        if (nums.length != 2) {
            myException me = new myException("Nombre erroni d'arguments (al seleccionar el tamany i el nombre de regions)");
            throw me;
        }
        int tam = Integer.parseInt(nums[0]);
        int nreg = Integer.parseInt(nums[1]);
        if (tam < 3 || tam > 9) {
            myException me = new myException("Tamany invalid per a un KenKen");
            throw me;
        }
        else if (nreg < 1 || nreg > tam*tam) {
            myException me = new myException("Nombre de regions invalid per a un KenKen de tamany: " + tam);
            throw me;
        }
        aux.add(tam);
        aux.add(nreg);
        cap.add(aux);
        ArrayList<Integer> xs = new ArrayList<>();
        ArrayList<Integer> ys = new ArrayList<>();
        for (int i = 0; i < nreg; ++i) {
            aux = new ArrayList<>();
            try {
                if (lineas[i+1] == "") {};
            }
            catch (IndexOutOfBoundsException ie) {
                myException me = new myException("Masses poques regions definides (nombre regions = " + nreg + ")");
                throw me;
            }
            nums = lineas[i+1].split(" ");
            int op = Integer.parseInt(nums[0]);
            try {
                if (nums[1] == "") {};
            }
            catch (IndexOutOfBoundsException ie) {
                myException me = new myException("No s'ha definit ni resultat ni nombre de caselles per a la regio amb operacio " + op);
                throw me;
            }
            int res = Integer.parseInt(nums[1]);
            try {
                if (nums[2] == "") {};
            }
            catch (IndexOutOfBoundsException ie) {
                myException me = new myException("No s'ha definit nombre de caselles per a la regio amb operacio " + op + " i resultat " + res);
                throw me;
            }
            int ncas = Integer.parseInt(nums[2]);
            if (op < 0 || op > 6) {
                myException me = new myException("L'operacio " + op + " no existeix");
                throw me;
            }
            if (ncas < 1 || ncas > (tam*tam)) {
                myException me = new myException("Nombre de caselles invalid per al tamany: " + tam);
                throw me;
            }
            if (op == 0 && ncas != 1) {
                myException me = new myException("Nombre de caselles invalid per a l'operacio 0");
                throw me;
            }
            else if (ncas != 2 && (op == 2 || op == 4 || op == 5 || op == 6)) {
                myException me = new myException("Nombre de caselles invalid per a l'operacio " + op);
                throw me;
            }
            aux.add(op);
            aux.add(res);
            aux.add(ncas);
            int extra = 0;
            for (int j = 0; j < ncas*2; j += 2) {
                int it = j+3+extra;
                try {
                    int x = Integer.parseInt(nums[it]);
                    int y = Integer.parseInt(nums[it+1]);
                    if (x < 1 || x > tam || y < 1 || y > tam) {
                        myException me = new myException("Posicio de la casella (x = " + x + " i y = " + y + ") incorrecta");
                        throw me;
                    }
                    aux.add(x);
                    aux.add(y);
                    xs.add(x);
                    ys.add(y);
                    if (it+2 < nums.length && nums[it+2].startsWith("[") && nums[it+2].endsWith("]")) {
                        String aux2 = nums[it+2].substring(1,nums[it+2].length()-1);
                        int v = Integer.parseInt(aux2) + 10;
                        if (v < 11 || v > 10+tam) {
                            int vaux = v -10;
                            myException me = new myException("El valor " + vaux + " no es valid per a la casella amb x = " + x + " i y = " + y);
                            throw me;
                        }
                        extra += 1;
                        aux.add(v);
                    }
                    else if (j + 2 >= ncas*2 && it+2 < nums.length) {
                        myException me = new myException("Mes caselles definides que el nombre de caselles (nombre de caselles = " + ncas + ") establert a la zona amb operacio " + op + " i resultat " + res);
                        throw me;
                    }
                }
                catch (IndexOutOfBoundsException ib) {
                    myException me = new myException("Insuficients caselles definides per a la zona amb operacio " + op + " i resultat " + res);
                    throw me;
                }
            }
            cap.add(aux);
        }
        if (xs.size() != tam*tam) {
            myException me = new myException("S'han definit menys o mes caselles de les corresponents pel tamany: " + tam + "x" + tam);
            throw me;
        }
        for (int i = 0; i < tam*tam; ++i) {
            for(int j = i + 1; j < tam*tam; ++j) {
                if (xs.get(i) == xs.get(j) && ys.get(i) == ys.get(j)) {
                    myException me = new myException("Casella amb posicio: x = " + xs.get(i) + " i y = " + ys.get(i) + " repetida");
                    throw me;
                }
            }
        }
        KenKen K = Crea_kenken(cap);
        Valida_kenken(K);
        if (b) afegeixKenKen(K);
    }
    
    /** Guarda el KenKen passat per paràmetre a persistència
     * @param K KenKen a guardar a la base de dades */
    private void afegeixKenKen(KenKen K){
        int q = quantsKenKens();
        String id = ""+q;
        String k = revertirFormatoKenKen(K);
        CP.afegeixKenKen(id, k);
    }

    /** Retorna el tamany actual del Ranking de dificultat dif
     * @param dif Dificultat del rànquing a consultar */
    public int tamanyRanking(int dif){
        return CP.tamanyRanking(dif);
    }

    /** Actualitza el Ranking de dificultat dif, amb un usuari i punts passats per paràmetre
     * @param dif Dificultat del rànquing a modificar
     * @param nom Nom de l'usuari
     * @param punts Puntuació obtinguda a la partida */
    public void actualitzarRanking(int dif, String nom, int punts){
        CP.actualitzarRanking(dif, nom, punts);;
    }

    /** Retorna el KenKen obtingut a partir de convertir el string passat per paràmetre 
     * @param kenkenString String el qual es convertirà a kenken */
    public static KenKen convertirFormatoKenKen(String kenKenString) {
        String[] filas = kenKenString.split("\n");
        String[] ele = filas[0].trim().split(" ");

        int tamany = Integer.parseInt(ele[0]);
        KenKen k = new KenKen(tamany);

        for (int i = 0; i < filas.length-1; i++) {
            String[] elementos = filas[i+1].trim().split(" ");
                int op = Integer.parseInt(elementos[0]);
                int res = Integer.parseInt(elementos[1]);
                Zona z = new Zona(res, op);
                int ncas = Integer.parseInt(elementos[2]);

                for(int j = 0; j<ncas; ++j){
                    int x = Integer.parseInt(elementos[3+j*3])-1;
                    int y = Integer.parseInt(elementos[3+j*3+1])-1;
                    Casella c = new Casella(i,x,y);

                    String val = elementos[3+j*3+2].substring(1, elementos[3+j*3+2].length()-1);
                    int v =  Integer.parseInt(val);
                    c.setValor(v);
                    if(elementos[3+j*3+2].startsWith("[")) c.setPre();

                    z.setCasella(c);
                    k.setCasella(c, x, y);
                }
                k.setZona(z);
        }
        return k;
    }

    /** Retorna un string obtingut a partir de convertir el KenKen passat per paràmetre 
     * @param k KenKen que es convertirà en String */
    public static String revertirFormatoKenKen(KenKen k) {
        StringBuilder kenkenString = new StringBuilder();
        int nzonas = k.consultaTamanyZones();
        kenkenString.append( k.consultaTamany()).append(" ").append(nzonas).append("\n");

        for(int i = 0; i < nzonas; ++i){
            Zona z = k.consultaZona(i);
            int ncas = z.consultaTamanyCaselles();
            kenkenString.append(z.consultaOperacio()).append(" ").append(z.consultaTotal()).append(" ").append(ncas);
            for(int j = 0; j < ncas; ++j){
                Casella c = z.consultaCasella(j);
                kenkenString.append(" ").append(c.getX()+1).append(" ").append(c.getY()+1);
                if(c.getPre()){
                    kenkenString.append(" [").append(c.consultaValor()).append("]");
                }
                else {
                        kenkenString.append(" (").append(c.consultaValor()).append(")");
                }
            }
            kenkenString.append("\n");
        }

        return kenkenString.toString();
    }

    /** Retorna la partida obtinguda a partir de convertir el string passat per paràmetre
     * @param partidaString String el qual es convertirà a partida */
    public Partida convertirFormatoPartida(String partidaString) {
        String[] ele = partidaString.trim().split(" ");
        String user = ele[0];
        int idp = Integer.parseInt(ele[1]);
        int idk = Integer.parseInt(ele[2]);

        int pistes = Integer.parseInt(ele[ele.length-4]);
        int errores = Integer.parseInt(ele[ele.length-3]);
        int fin = Integer.parseInt(ele[ele.length-2]);
        int time = Integer.parseInt(ele[ele.length-1]);


        String kenken;
        String kenkenaux;
        KenKen k;
        KenKen kaux = new KenKen();
        if (ele[ele.length-5]== "-1"){
            if (ele.length > 8) {
                kenken = String.join(" ", Arrays.copyOfRange(ele, 3, ele.length - 5));
                k = convertirFormatoKenKenP(kenken);
            }
            else{
                k = new KenKen();
            }
        }
        else{
            int op = Integer.parseInt(ele[4]);
            int tamanyKenken = 0;
            for(int i = 0; i < op; ++i){
                int ncas = Integer.parseInt(ele[4+tamanyKenken+3]);
                tamanyKenken += ncas*3+3;
            }

            if (ele.length > 8) {
                kenken = String.join(" ", Arrays.copyOfRange(ele, 3, 5+tamanyKenken));
                k = convertirFormatoKenKenP(kenken);
            }
            else{
                k = new KenKen();
            }

            kenkenaux = String.join(" ", Arrays.copyOfRange(ele,5+tamanyKenken , ele.length - 4));
            kaux = convertirFormatoKenKenP(kenkenaux);
        }

        Jugador j = getJugador(user);
        Partida p = new Partida(j, idp, idk, k);
        p.setPistes(pistes);
        p.setErrors(errores);
        if(fin == 1) p.setEstat();
        Timer t = new Timer(time);
        p.setTimer(t);
        p.setKenkenAleatoriSol(kaux);
        return p;
    }
    
    /** Retorna el KenKen obtingut a partir de convertir el string passat per paràmetre (amb un format diferent a revertirFormatoKenKen(KenKen k))
     * @param kenkenString String el qual es convertirà a kenken */
    public static KenKen convertirFormatoKenKenP(String kenKenString) {
        String[] ele = kenKenString.trim().split(" ");

        int tamany = Integer.parseInt(ele[0]);
        int nreg = Integer.parseInt(ele[1]);
        KenKen k = new KenKen(tamany);

        int aux = 0;

        for (int i = 0; i < nreg; i++) {
                int op = Integer.parseInt(ele[2+aux+i*3]);
                int res = Integer.parseInt(ele[3+aux+i*3]);
                Zona z = new Zona(res, op);
                int ncas = Integer.parseInt(ele[4+aux+i*3]);

                for(int j = 0; j<ncas; ++j){
                    int x = Integer.parseInt(ele[5+aux+i*3+j*3])-1;
                    int y = Integer.parseInt(ele[6+aux+i*3+j*3])-1;
                    Casella c = new Casella(i,x,y);

                    int v = Integer.parseInt(ele[7+aux+i*3+j*3].substring(1,ele[7+aux+i*3+j*3].length()-1));
                    c.setValor(v);
                    if(ele[7+aux+i*3+j*3].startsWith("[")){c.setPre();}
                    z.setCasella(c);
                    k.setCasella(c, x, y);
                }
                aux += ncas*3;
                k.setZona(z);
        }
        return k;
    }

    /** Retorna un string obtingut a partir de convertir el KenKen passat per paràmetre (amb un format diferent a convertirFormatoKenKen(String kenKenString))
     * @param k KenKen el qual es convertirà en String */
    public static String revertirFormatoKenKenP(KenKen k) {
        StringBuilder kenkenString = new StringBuilder();
        int nzonas = k.consultaTamanyZones();
        kenkenString.append( k.consultaTamany()).append(" ").append(nzonas).append(" ");

        for(int i = 0; i < nzonas; ++i){
            Zona z = k.consultaZona(i);
            int ncas = z.consultaTamanyCaselles();
            kenkenString.append(z.consultaOperacio()).append(" ").append(z.consultaTotal()).append(" ").append(ncas);
            for(int j = 0; j < ncas; ++j){
                Casella c = z.consultaCasella(j);
                kenkenString.append(" ").append(c.getX()+1).append(" ").append(c.getY()+1);
                if(c.getPre()){
                    kenkenString.append(" [").append(c.consultaValor()).append("]");
                }
                else {
                    kenkenString.append(" (").append(c.consultaValor()).append(")");
                }
            }
            kenkenString.append(" ");
        }
        return kenkenString.toString();
    }

    /** Guarda a persistència la partida passada per paràmetre per l’usuari user amb identificador de partida idp
     * @param user Nom del usuari
     * @param idp Identificador de la partida 
     * @param p Partida a guardar a la base de dades */
    public void afegeixPartida(String user, int idp, Partida p){
        StringBuilder partidaString = new StringBuilder();
        partidaString.append(user).append(" ").append(idp).append(" ").append(p.getIdk()).append(" ");
        String k = revertirFormatoKenKenP(p.getKenken());
        int fin = 0;
        if(p.getEstat()) fin = 1;
        partidaString.append(k);

        if(p.consultaTamanyKSolGen() != -1){
            String kaux = revertirFormatoKenKenP(p.getKenkenSolAleatori());
            partidaString.append(kaux);
        }
        else{
            partidaString.append("-1").append(" ");
        }
        partidaString.append(p.getPistes()).append(" ").append(p.getErrors()).append(" ").append(fin).append(" ").append(p.getTime());
        String partida = partidaString.toString();
        CP.afegeixPartida(partida);
    }
    
    /** Esborra la partida identificada per idp de l’usuari user de persistència
     * @param user Nom del usuari
     * @param idp Identificador de la partida  */
    public void esborraPartida(String user, int idp){
        String idPartida = String.valueOf(idp);
        CP.esborraPartida(user, idPartida);
    }
}