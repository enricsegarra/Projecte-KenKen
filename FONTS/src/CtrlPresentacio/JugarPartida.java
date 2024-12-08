package CtrlPresentacio;

import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

import CtrlDomini.CtrlDomini;

/**Classe que hereta de JPanel (és un panell) on es mostra el kenken seleccionat i, si es desitja, s'inicia i es juga la partida*/
public class JugarPartida extends JPanel {
    /**Botó per sol·licitar una pista*/
    private JButton pista;
    /**Botó per a sol·licitar la solució del kenken*/
    private JButton solucionar;
    /**Botó per sortir de la partida*/
    private JButton sortir;
    /**Botó per iniciar la partida*/
    private JButton jugar;
    /**Botó per retornar a la vista anterior abans d'iniciar la partida*/
    private JButton enrere;
    /**Enter amb la dificultat del kenken*/
    private int dificultat;
    /**Matriu de zones de text que conformen totes les caselles del kenken*/
    private JTextField casillas[][];
    /**Vector d'etiquetes amb la informació de cada regió del kenken*/
    private JLabel lbloperacio[];
    /**Vector amb els subpanells on aniran les etiquetes de les regions*/
    private JPanel overpanel[];
    /**Enter amb l'identificador de la partida actual*/
    private int id;
    /**Booleà que indica si la partida ha sigut finalitzada o no*/
    private boolean p;
    /**Vora per a quadrar la composició dels elements al panell*/
    private Border border;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel gpanel;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel mpanel;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel mpanel2;
    /**Etiqueta amb la informacio necessaria per al usuari */
    private JLabel txtsuperior;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel auxpanel;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel cpanel;
    /**Controlador de presentació*/
    private CtrlPresentacio iCpres;
    /**Controlador de domini*/
    private CtrlDomini CD;
    /**Booleà que indica si el kenken de la partida ha sigut generat aleatòriament*/
    private boolean generat;

    /**Creadora de la classe JugarPartida, se li passa el controlador de presentació i el controlador de domini com a paràmetres
     * @param cpres Controlador de presentació
     * @param CDom Controlador de domini */
    public JugarPartida(CtrlPresentacio cpres, CtrlDomini CDom) {
        iCpres = cpres;
        CD = CDom;
    }

    /**Mètode public per a assignar la dificultat del kenken i l'identificador de la partida (es passen com a paràmetres)
     * @param dif Mida del kenken de la partida
     * @param id2 Identificador de la partida */
    public void setAtributs(Integer dif, int id2) {
        id = id2;
        dificultat = dif;
        p = false;
        generat = (CD.consultaTamanyKSolGen() != -1);
        Color grisc = new Color(228,228,228);
        setBackground(grisc);
        inicialitzarComponents();
    }

    /**Mètode privat que inicialitza tots els components de la vista i els assigna a aquesta*/
    private void inicialitzarComponents() {
        setLayout(new BorderLayout());
        
        Color grisf = new Color(55,58,64);
        Color tar = new Color(220,95,0);
        Color grisc = new Color(228,228,228);

        int nzones = CD.consultaTamanyZones(id);
        boolean zonaMarcada[] = new boolean[nzones];
        overpanel = new JPanel[nzones];

        auxpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        auxpanel.setOpaque(false);
        cpanel = new JPanel();
        cpanel.setOpaque(false);
        cpanel.setLayout(new GridLayout(dificultat,dificultat));
        cpanel.setPreferredSize(new Dimension(240+(dificultat-3)*55,240+(dificultat-3)*55));
        cpanel.setBorder( BorderFactory.createLineBorder(grisc, 40));
        casillas = new JTextField[dificultat][dificultat];
        lbloperacio = new JLabel[nzones];

        
        for (int i = 0; i < dificultat; ++i) {
            for (int j = 0; j < dificultat; ++j) {
                int idz = CD.consultaIdz(i, j, id);

                casillas[i][j] = new JTextField();
                casillas[i][j].setEnabled(false);
                setBordesZona(casillas[i][j], i, j, idz);
                
                Integer n = CD.consultaValor(i,j,id);
                Color col = new Color(0,0,0);
                casillas[i][j].setForeground(col);
                if (n != 0) {
                    casillas[i][j].setText(n.toString());
                    casillas[i][j].setDisabledTextColor(col);
                    if (CD.consultaPre(i,j)) {
                        col = new Color(75,75,75);
                        casillas[i][j].setDisabledTextColor(col);
                    }
                }
                PlainDocument doc = (PlainDocument) casillas[i][j].getDocument();
                doc.setDocumentFilter(new MyIntFilter(dificultat));
                casillas[i][j].getDocument().addDocumentListener(new DocumentListC(casillas[i][j],i,j,id));
                casillas[i][j].setFont(new Font("Arial",Font.BOLD,18));
                casillas[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                if (!zonaMarcada[idz]) {
                    zonaMarcada[idz] = true;
                    Integer op = CD.consultaOperacioZona(idz,id);
                    char opc = ' ';
                    Integer resultat = CD.consultaResultatZona(idz, id);

                    overpanel[idz] = new JPanel() {
                        public boolean isOptimizedDrawingEnabled() {
                           return false;
                        }
                     };
                    LayoutManager overlay = new OverlayLayout(overpanel[idz]);
                    overpanel[idz].setLayout(overlay);
                    overpanel[idz].setOpaque(false);
                    if (op == 1) opc = '+';
                    else if (op == 2) opc = '-';
                    else if (op == 3) opc = '*';
                    else if (op == 4) opc = '/';
                    else if (op == 5) opc = '^';
                    else if (op == 6) opc = '%';
                    if (op != 0) lbloperacio[idz] = new JLabel(opc + "" + resultat);
                    else lbloperacio[idz] = new JLabel("");
                    lbloperacio[idz].setFont(new Font("Arial",Font.PLAIN,12));
                    overpanel[idz].add(casillas[i][j]);
                    float auxx = 0.9f;
                    float auxy = 0.1f;
                    if (i == 0) auxy = auxy + 0.07f;
                    if (j == dificultat - 1) auxx = auxx - 0.07f;
                    lbloperacio[idz].setAlignmentX(auxx);
                    lbloperacio[idz].setAlignmentY(auxy);
                    casillas[i][j].setAlignmentX(auxx);
                    casillas[i][j].setAlignmentY(auxy);

                    overpanel[idz].add(lbloperacio[idz]);
                    overpanel[idz].add(casillas[i][j]);
                    cpanel.add(overpanel[idz]);
                }
                else {
                    cpanel.add(casillas[i][j]);
                }
                
            }
        }

        jugar = new JButton("Jugar");
        jugar.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        jugar.setBackground(tar);
        jugar.setForeground(grisc);
        ListenerBotonJ Ljugar = new ListenerBotonJ();
        jugar.addActionListener(Ljugar);

        enrere = new JButton("Enrere");
        enrere.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        enrere.setBackground(grisf);
        enrere.setForeground(grisc);
        ListenerBotonE Lenrere = new ListenerBotonE();
        enrere.addActionListener(Lenrere);

        sortir = new JButton("Sortir");
        sortir.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        sortir.setBackground(grisf);
        sortir.setForeground(grisc);
        ListenerBotonS Lsortir = new ListenerBotonS();
        sortir.addActionListener(Lsortir);
        
        solucionar = new JButton("Solucionar");
        solucionar.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        solucionar.setBackground(tar);
        solucionar.setForeground(grisc);
        ListenerBotonSol Lsolucionar = new ListenerBotonSol();
        solucionar.addActionListener(Lsolucionar);

        pista = new JButton("Pista");
        pista.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        pista.setBackground(tar);
        pista.setForeground(grisc);
        ListenerBotonP Lpista = new ListenerBotonP();
        pista.addActionListener(Lpista);

        gpanel = new JPanel(new GridLayout(1,2));
        gpanel.setOpaque(false);
        mpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mpanel.setOpaque(false);
        mpanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mpanel2.setOpaque(false);
        mpanel.add(jugar);
        mpanel2.add(enrere);
        auxpanel.add(cpanel);
        gpanel.add(mpanel);
        gpanel.add(mpanel2);
        JPanel aux = new JPanel(new FlowLayout(FlowLayout.CENTER));
        aux.setOpaque(false);
        txtsuperior = new JLabel("Vols jugar aquest KenKen?");
        txtsuperior.setHorizontalAlignment(SwingConstants.CENTER);
        txtsuperior.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        txtsuperior.setFont(new Font("Microsoft YaHei",Font.BOLD,18));
        aux.add(txtsuperior);
        add(txtsuperior, BorderLayout.NORTH);
        add(auxpanel, BorderLayout.CENTER);
        add(gpanel, BorderLayout.SOUTH);
    }

    /**Mètode que assigna les vores de les caselles més amples en cas que delimitin una regió
     * @param c Casella (Àrea de text) sobre la que s'actua
     * @param x Columna de la casella
     * @param y Fila de la casella
     * @param idz Identificador de la zona de la casella */
    private void setBordesZona(JTextField c, int x, int y, int idz) {
        int N = 1;
        int S = 1;
        int E = 1;
        int W = 1;

        if (x == 0) {
            N = 6;   // a dalt
        }
        else if (x == (dificultat-1)) {
            S = 6;  // a baix
        }
    
        if (y == 0) {
            W = 6;  // esquerra
        }
        else if (y == (dificultat-1)) {
            E = 6;  // dreta
        } 

        if (N != 6) if (CD.consultaIdz(x-1, y, id) != idz) N = 3;  // consulto a dalt si no es límit
        if (S != 6) if (CD.consultaIdz(x+1, y, id) != idz) S = 3;  // consulto a baix si no es límit
        if (W != 6) if (CD.consultaIdz(x, y-1, id) != idz) W = 3;  // consulto l'esquerra si no es límit
        if (E != 6) if (CD.consultaIdz(x, y+1, id) != idz) E = 3;  // consulto la dreta si no es límit

        border = new MatteBorder(N, W, S, E, Color.BLACK);
        c.setBorder(border);
    }

    /**Classe per al Listener del botó de sortir de la partida*/
    private class ListenerBotonS implements ActionListener {
        /**En aquest mètode es pregunta l'usuari si es vol guardar la partida abans de retornar al menú principal (a no ser que s'hagi finalitzat la partida), 
         * si es respon negativament es sol·licita una confirmació. Segons la resposta es guarda la partida o no
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            if (!p) {
                if (iCpres.Missatge(3, "Guardar partida", "Vols guardar la partida?")) {
                    iCpres.Missatge(2, "Partida guardada","S'ha guardat la partida amb id = " + id);
                    iCpres.guardarPartida(id,true,p);
                }
                else {
                    if (iCpres.Missatge(3, "Confirmació no guardar", "Estàs segur que no vols guardar la partida?")) {
                        iCpres.guardarPartida(id,false,p);
                    }
                    else {
                        iCpres.Missatge(2, "Partida guardada","S'ha guardat la partida amb id = " + id);
                        iCpres.guardarPartida(id,true,p);
                    }
                }
            }
            else iCpres.guardarPartida(id,false,p);
        }
    }

    /**Classe per al Listener del botó de solucionar*/
    private class ListenerBotonSol implements ActionListener {
        /**Per a aquest mètode es demana una confirmació sobre si es vol solucionar. En cas afirmatiu es soluciona el kenken, s'inhabiliten totes les funcionalitats menys sortir i es posen totes les caselles de color verd
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            if (iCpres.Missatge(3, "Solucionar KenKen", "Segur que vols solucionar el KenKen?")) {
                p = true;
                for (int i = 0; i < dificultat; ++i) {
                    for (int j = 0; j < dificultat; ++j) {
                        if (!CD.consultaPre(i,j)) {
                            try {
                                casillas[i][j].getDocument().remove(0,casillas[i][j].getDocument().getLength());
                            }
                            catch(Exception e) {}
                        }
                    }
                }
                Color col = new Color(85,255,85);
                Color col2 = new Color(50,50,50);
                for (int i = 0; i < dificultat; ++i) {
                    for (int j = 0; j < dificultat; ++j) {
                        casillas[i][j].setBackground(col);
                        if (!CD.consultaPre(i,j)) {
                            Integer n;
                            if (generat) n = CD.consultaValorSolucioGen(i,j,id);
                            else n = CD.consultaValorSolucio(i,j,id);
                            casillas[i][j].setText(n.toString());
                            casillas[i][j].setDisabledTextColor(col2);
                            casillas[i][j].setEnabled(false);
                        }
                    }
                }
                solucionar.setEnabled(false);
                pista.setEnabled(false);
                int punts = iCpres.finalitzarPartida(false);
                if (punts == 0) {} //Per evitar warning, es pot eliminar
                iCpres.Missatge(2, "Fi partida", "S'ha finalitzat la partida. Com s'ha demanat la solució la partida no té puntuació");
            }
        }
    }

    /**Classe per al Listener del botó de sol·licitar una pista*/
    private class ListenerBotonP implements ActionListener {
        /**Aleatòriament busca una casella buida, sol·licita un valor possible per a aquella casella i en cas d'haver-hi l'introdueix i la casella es posa de color groc.
         * Si no hi ha cap valor possible es mostra un missatge informatiu. En els dos casos es suma una pista a la partida
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            boolean l = true;
            int x,y;
            while (l) {
                Random random = new Random();
                x = random.nextInt(dificultat);
                y = random.nextInt(dificultat);
                if (casillas[x][y].getText().isEmpty()) {
                    l = false;
                    int n = CD.consultaPista(x,y);
                    if (n == -1) iCpres.Missatge(2, "Pista", "El KenKen, amb els valors ja introduïts, no té una possible solució. S'augmenta en 1 les pistes demanades");
                    else {
                        Integer m = n;
                        CD.BorrarValor(x,y,id);
                        Color col = new Color(255,255,85);
                        casillas[x][y].setBackground(col);
                        casillas[x][y].setText(m.toString());
                    }
                    CD.sumaPista();
                }
            }
        }
    }

    /**Classe per al Listener del botó de retornar a la vista anterior (només visible abans d'iniciar la partida)*/
    private class ListenerBotonE implements ActionListener {
        /**Aquest mètode retorna a la vista anterior a l'actual (pot ser GeneraKenKen, Seleccionar o SeleccioContinuar)
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            if (CD.consultaTamanyKaux() > 0 && CD.partidesJugadorSize() == 1) iCpres.tornarMP();
            else if (CD.consultaTamanyKaux() > 0) iCpres.seleccionaContinuar();
            else if (CD.consultaTamanyKSolGen() > 0) iCpres.generaAleatori();
            else if (CD.quantsKenKens() == 1) iCpres.tornarMP();
            else iCpres.tornarSK();
        }
    }

    /**Classe per al Listener del botó de jugar (només visible abans d'iniciar la partida)*/
    private class ListenerBotonJ implements ActionListener {
        /**Mètode que inicia la partida (i cambia els subpanells i habilita totes les caselles menys les que tenen valors predefinits)
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            mpanel.remove(jugar);
            mpanel2.remove(enrere);
            
            mpanel.add(solucionar);
            mpanel.add(pista);
            mpanel2.add(sortir);
            
            for (int i = 0; i < dificultat; ++i) {
                for (int j = 0; j < dificultat; ++j) {
                    if (!CD.consultaPre(i,j)) {
                        casillas[i][j].setEnabled(true);
                    }
                }
            }
            
            CD.iniciarTimer();
            remove(txtsuperior);
            validate();

        }
    }

    /**Classe per al Listener de les àrees de text*/
    private class DocumentListC implements DocumentListener {
        /**Enter amb la fila de la casella*/
        private int x;
        /**Enter amb la columna de la casella*/
        private int y;
        /**Enter amb el identificador de la partida*/
        private int id;
        /**Àrea de text que representa la casella*/
        private JTextField jt;

        /**Creadora de la classe DocumentListC
         * @param jtext Àrea de text que representa la casella
         * @param i Columna de la casella
         * @param j Fila de la casella
         * @param id2 Identificador de la partida */
        public DocumentListC(JTextField jtext, int i, int j, int id2) {
            jt = jtext;
            x = i;
            y = j;
            id = id2;
        }

        /**Mètode que es crida cada cop que s'introdueix un valor a la casella. Comprova que el valor introduït sigui correcte (en cas negatiu, suma un error, mostra un missatge d'error, pinta la casella de vermell 
         * i inhabilita tot menys la casella erronea i el botó de solucionar). També comprova, en cas afirmatiu i després d'assignar el valor a la casella, que el kenken no s'hagi completat visitant totes les caselles. 
         * Si s'ha completat es finalitza la partida, es posen totes les caselles de verd i s'inhabiliten les caselles i els botons solucionar i demanar pista
         * @param e Acció que es realitza sobre l'àrea de text */
        @Override
        public void insertUpdate(DocumentEvent e) {
            
            int v = Integer.parseInt(jt.getText());
            if (!CD.introdueixValor(x,y,v,id)) {
                iCpres.Missatge(1,"Valor incorrecte","El valor no és correcte, es suma un error");
                CD.sumaError();
                Color col = new Color(255,85,85);
                Color col2 = new Color(100,100,100);
                casillas[x][y].setBackground(col);
                for (int k = 0; k < dificultat; ++k) {
                    for (int m = 0; m < dificultat; ++m) {
                        if (casillas[k][m] != casillas[x][y]) {
                            casillas[k][m].setEnabled(false);
                            casillas[k][m].setDisabledTextColor(col2);
                        }
                    }
                }
                pista.setEnabled(false);
                sortir.setEnabled(false);
            }
            else if (!p) {
                int i = 0;
                p = true;
                while (p && i < dificultat) {
                    int j = 0;
                    while (p && j < dificultat) {
                        if (CD.consultaValor(i, j, id) == 0) p = false;
                        ++j;
                    }
                    ++i;
                }
                if (p) {
                    Color col = new Color(85,255,85);
                    Color col2 = new Color(0,0,0);
                    for (int k = 0; k < dificultat; ++k) {
                        for (int m = 0; m < dificultat; ++m) {
                            casillas[k][m].setBackground(col);
                            casillas[k][m].setEnabled(false);
                            casillas[k][m].setDisabledTextColor(col2);
                        }
                    }
                    solucionar.setEnabled(false);
                    pista.setEnabled(false);
                    int punts = iCpres.finalitzarPartida(true);
                    iCpres.Missatge(2,"Fi partida","Felicitats! Has completat el KenKen. La partida s'ha finalitzat amb " + punts + " punts");
                }
            }
        }

        /**Mètode que es crida cada cop que s'introdueix un valor a la casella. Elimina el valor del kenken i de l'àrea de text i posa el color a blanc independentment del color que tingui
         * @param e Acció que es realitza sobre l'àrea de text */
        @Override
        public void removeUpdate(DocumentEvent e) {
            Color col = new Color(255,255,255);
            casillas[x][y].setBackground(col);
            if (CD.consultaValor(x, y, id) == 0) {
                for (int k = 0; k < dificultat; ++k) {
                    for (int m = 0; m < dificultat; ++m) casillas[k][m].setEnabled(true);
                }
                pista.setEnabled(true);
                sortir.setEnabled(true);
            }
            else CD.BorrarValor(x,y,id);
        }

        /**Mètode que es crida quan es canvia la font o altres coses de l'àrea de text. Només s'ha buidat per seguretat i per fer l'Override
         * @param e Acció que es realitza sobre l'àrea de text */
        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    /**Classe anidada que actua com a filtre de les àrees de text, on només permet escriure un nombre entre 1 i la dificultat del kenken*/
    class MyIntFilter extends DocumentFilter { 
        /**Enter amb la dificultat del kenken*/
        private int dif;

        /**Creadora de la classe MyIntFilter. Se li passa la dificultat del kenken com a paràmetre
         * @param dificultat Dificultat del kenken */
        public MyIntFilter(int dificultat) {
            dif = dificultat;
        }

        /**Mètode privat que comprova si el valor introduït és un nombre entre 1 i la dificultat del kenken. En cas afirmatiu retorna true, altrament es retorna false
         * @param text Text introduit a l'àrea de text */
        private boolean test(String text) {
           try {
              if (text.isEmpty()) return true;
              int n = Integer.parseInt(text);
              if (n != 0 && n <= dif) return true;
              return false;
           } catch (NumberFormatException e) {
              return false;
           }
        }
     
        /**Mètode que es crida cada cop que s'introdueix un valor a la casella. Només deixa cambiar el valor de la casella si es cumpleixen les condicions del mètode test(String text)*/
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
              AttributeSet attrs) throws BadLocationException {
    
           Document doc = fb.getDocument();
           String txtActual = doc.getText(0, doc.getLength());
           StringBuilder sb = new StringBuilder();
           sb.append(doc.getText(0, doc.getLength()));
           sb.replace(offset, offset + length, text);
     
           if (test(sb.toString()) && txtActual.length() == 0) {
              super.replace(fb, offset, length, text, attrs);
           }
        }
    }
}


