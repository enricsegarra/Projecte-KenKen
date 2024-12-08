package CtrlPresentacio;

import javax.swing.*;

import CtrlDomini.*;

/**Classe del controlador de presentació*/
public class CtrlPresentacio {

    /**Controlador de domini*/
    private CtrlDomini CD;
    /**Frame principal de l'aplicació*/
    private JFrame JF;
    /**Vista de gestió de perfil, la primera que es mostrarà*/
    private IniciSessio IS;
    /**Vista del menú principal, on es pot seleccionar jugar una nova partida, introduir un kenken, continuar una partida, visualitzar els rànquings o tancar sessió*/
    private MenuPrincipal MP;
    /**Vista per escollir un mètode a l'hora de jugar una nova partida (seleccionar un kenken guardat a persistència o generar-ne un d'aleatori)*/
    private EscullMetode EM;
    /**Vista per a introduir un kenken, ja sigui per a comprovar la seva validesa o guardar-lo a persistència (si és vàlid)*/
    private IntroduirKenKen IK;
    /**Vista per a seleccionar la dificultat a l'hora de generar un kenken aleatori*/
    private GeneraKenKen GK;
    /**Vista per a visualitzar els diferents rànquings segons la seva dificultat*/
    private VistaRankings VR;
    /**Vista per a seleccionar el kenken amb el qual iniciar una nova partida segons el seu identificador*/
    private Seleccionar SK;
    /**Vista per a jugar una partida*/
    private JugarPartida JP;
    /**Vista per a seleccionar la partida que es vol reprendre segons el seu identificador de partida*/
    private SeleccioContinuar SC;

    /**Creadora del control de presentació, la qual crea un nou controlador de domini*/
    public CtrlPresentacio() {
        CD = new CtrlDomini();
    }

    /**Inicialitza el frame principal de l'aplicació JF, crea i assigna la vista IniciSessio i fa visible el frame*/
    public void inicialitzarPresentacio() {
        JF = new JFrame();
        IS = new IniciSessio(this);
        JF.setTitle("Gestió Perfils");
        JF.setSize(358,200);
        JF.setLocationRelativeTo(null);
        JF.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JF.setContentPane(IS);
        JF.setVisible(true);
    }

    /**Inicia sessió amb el username u i la contrasenya p, si existeix tal perfil crea una nova vista MenuPrincipal l'assigna a JF i es fa visible, altrament mostra un missatge d'error
     * @param u Nom d'usuari del jugador
     * @param p Contrasenya del jugador */
    public void iniciarSessio(String u, String p) {
        int aux = CD.iniciarSessio(u, p);
        if (aux == 0) {
            MP = new MenuPrincipal(this);
            JF.setContentPane(MP);
            JF.setTitle("Menú Principal");
            JF.validate();
            JF.setSize(600,600);
            JF.setLocationRelativeTo(null);
        }
        else if (aux == 1) IniciSessio.MissatgeError("Error al iniciar sessió","No existeix un perfil amb el nom d'usuari " + u);
        else IniciSessio.MissatgeError("Error al iniciar sessió","Contrasenya incorrecta");
    }

    /**Crea un nou perfil amb el username u i la contrasenya p. Si el perfil ja existeix mostra un missatge d'error
     * @param u Nom d'usuari del jugador
     * @param p Contrasenya del jugador */
    public void crea(String u, String p) {
        if (CD.creaPerfil(u, p)) {
            IniciSessio.MissatgeInfo( "Creació de perfil", "S'ha creat el perfil correctament");
        }
        else {
            IniciSessio.MissatgeError("Error al crear perfil", "Ja existeix l'usuari: " + u);
        }
    }

    /**Borra un perfil amb el username u i la contrasenya p. Si el perfil no existeix o la contrasenya és incorrecta mostra un missatge d'error
     * @param u Nom d'usuari del jugador
     * @param p Contrasenya del jugador */
    public void borra(String u, String p) {
        int aux = CD.ConsultaBorraPerfil(u, p);
        if (aux == 0) {
            if (IniciSessio.Confirmacio("Esborrar Perfil", "Estàs segur que vols esborrar aquest perfil?")) {
                CD.esborraJugador(u);
            }
        }
        else if (aux == 1) IniciSessio.MissatgeError("Error al esborrar perfil","No existeix un perfil amb el nom d'usuari " + u);
        else IniciSessio.MissatgeError("Error al esborrar perfil","Contrasenya incorrecta");
    }

    /**Crea una nova vista IniciSessio, l'assigna a JF i es fa visible*/
    public void tancaSessio() {
        IS = new IniciSessio(this);
        JF.setContentPane(IS);
        JF.setTitle("Gestió Perfils");
        JF.validate();
        JF.setSize(358,200);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista EscullMetode, l'assigna a JF i es fa visible*/
    public void escullMetode() {
        EM = new EscullMetode(this);
        JF.setContentPane(EM);
        JF.setTitle("Escull Metode");
        JF.validate();
        JF.setSize(375, 150);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista MenuPrincipal, l'assigna a JF i es fa visible*/
    public void tornarMP() {
        MP = new MenuPrincipal(this);
        JF.setContentPane(MP);
        JF.setTitle("Menú Principal");
        JF.validate();
        JF.setSize(600,600);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista EscullMetode, l'assigna a JF i es fa visible*/
    public void tornarEM() {
        EM = new EscullMetode(this);
        JF.setContentPane(EM);
        JF.setTitle("Escull Metode");
        JF.validate();
        JF.setSize(375, 150);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista Seleccionar, l'assigna a JF i es fa visible*/
    public void tornarSK() {
        SK = new Seleccionar(this);
        JF.setContentPane(SK);
        JF.setTitle("Selecciona Kenken");
        JF.validate();
        JF.setSize(375, 150);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista IntroduirKenKen, l'assigna a JF i es fa visible*/
    public void introKenKen() {
        IK = new IntroduirKenKen(this);
        JF.setContentPane(IK);
        JF.setTitle("Introduir KenKen");
        JF.validate();
        JF.setSize(825,525);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista generaKenKen, l'assigna a JF i es fa visible*/
    public void generaAleatori() {
        GK = new GeneraKenKen(this);
        JF.setContentPane(GK);
        JF.setTitle("Generar KenKen");
        JF.validate();
        JF.setSize(375, 150);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una partida amb un kenken aleatori de mida tam, crea una nova vista JugarPartida, li passa com a atributs el id de la partida i la mida, l'assigna a JF i la fa visible
     * @param tam Mida del kenken a generar */
    public void generaKenKen(Integer tam) {
        int idp = CD.iniciarPartida2(tam);
        JP = new JugarPartida(this, CD);
        JP.setAtributs(tam, idp);
        JF.setContentPane(JP);
        JF.setTitle("Jugar partida");
        JF.validate();
        JF.setSize(400+((tam-3)*55),325+((tam-3)*55));
        JF.setLocationRelativeTo(null);
    }

    /**Si el id del kenken és -1 continua la partida amb l'identificador idp, altrament inicia una partida amb el kenken amb l'identificador id que es guarda amb un id de partida idp. 
     * Crea una nova vista JugarPartida, li passa com a atributs el id de la partida i la mida, l'assigna a JF i la fa visible
     * @param id Identificador del kenken
     * @param idp Identificador de la partida */
    public void jugaKenKen(int id, int idp) {
        if (id == -1) {
            CD.copiaKenKenPartida(idp);
        }
        else {
            idp = CD.iniciarPartida(id);
        }
        int tam = CD.consultarTamany(idp);
        JP = new JugarPartida(this, CD);
        JP.setAtributs(tam, idp);
        JF.setContentPane(JP);
        JF.setTitle("Jugar partida");
        JF.validate();
        JF.setSize(400+((tam-3)*55),325+((tam-3)*55));
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista VistaRankings, l'assigna a JF i la fa visible*/
    public void vistaRankings() {
        VR = new VistaRankings(this);
        JF.setContentPane(VR);
        JF.setTitle("Rànquings");
        JF.validate();
        JF.setSize(800, 500);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista Seleccionar, l'assigna a JF i la fa visible*/
    public void seleccionaKenKen() {
        SK = new Seleccionar(this);
        JF.setContentPane(SK);
        JF.setTitle("Selecciona Kenken");
        JF.validate();
        JF.setSize(375, 150);
        JF.setLocationRelativeTo(null);
    }

    /**Crea una nova vista SeleccioContinuar, l'assigna a JF i la fa visible*/
    public void seleccionaContinuar() {
        SC = new SeleccioContinuar(this);
        JF.setContentPane(SC);
        JF.setTitle("Selecciona partida");
        JF.validate();
        JF.setSize(375, 150);
        JF.setLocationRelativeTo(null);
    }

    /**Guarda, no guarda o elimina una partida depenent dels booleans que es passen com a paràmetres. Despres crea una nova vista MenuPrinciapl, l'assigna a JF i la fa visible.
     * @param idp Identificador de la partida
     * @param q Booleà que indica si es guarda la partida o no 
     * @param p Booleà que indica si la partida s'ha completat o no*/
    public void guardarPartida(int idp, boolean q, boolean p) {
        if (!q && p && CD.consultaTamanyKaux() != -1) CD.borrarPartida(idp);
        else if (!q && CD.consultaTamanyKaux() != -1) CD.KenKenNoGuarda();
        else if (q) CD.guardarPartida(idp);
        MP = new MenuPrincipal(this);
        JF.setContentPane(MP);
        JF.setTitle("Menú Principal");
        JF.validate();
        JF.setSize(600,600);
        JF.setLocationRelativeTo(null);
    }

    /**Finalitza una partida 
     * @param b Booleà que indica si s'ha completat amb normalitat (true) o si s'ha demanat la solució del kenken (false)*/
    public int finalitzarPartida(boolean b) {
        return CD.finalitzarPartida(b);
    }

    /**Crea (i vàlida) un kenken a partir del String s 
     * @param s KenKen amb format string
     * @param b Booleà que indica si s'ha de guardar el kenken o no */
    public void creaKenKen(String s, boolean b) throws Exception {
            CD.creaKenKen(s,b);
    }

    /**Retorna el nombre de KenKens que es troben a la capa de persistència*/
    public int quantsKenKens() {
        return CD.quantsKenKens();
    }

    /**Retorna el rànking de la dificultat dif
     * @param dif Dificultat del rànquing a consultar
    */
    public String mostrar_ranking(String dif) {
        try {
            return CD.mostrar_ranking(dif);
        }
        catch (Exception e) {
            return "Sembla que el rànquing es vuit...";
        }
    }

    /**Retorna el nombre de partides a la capa de persistència per al jugador actual*/
    public int partidesJugadorSize() {
        return CD.partidesJugadorSize();
    }

    /**Crea una finestra amb un missatge. El tipus de finestra varia segons l'enter tipus que se li passa. Retorna un booleà per si la finestra és de selecció
     * @param tipus Varia entre 1 i 3, per a saber quin tipus de finestra mostrar (d'error, informativa, o de selecció)
     * @param S1 Títol de la nova finestra
     * @param S2 Missatge que es mostrarà a la nova finestra */
    public boolean Missatge(int tipus, String S1, String S2) {
        if (tipus == 1) IniciSessio.MissatgeError(S1,S2);
        else if (tipus == 2) IniciSessio.MissatgeInfo(S1,S2);
        else return IniciSessio.Confirmacio(S1, S2);
        return false;
    }

}