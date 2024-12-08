package CtrlPresentacio;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**Classe que hereta de JPanel (és un panell) que actua com a menú principal de l'aplicació*/
public class MenuPrincipal extends JPanel {
    /**Botó per a iniciar una nova partida*/
    private JButton nueva;
    /**Botó per a introduir un nou KenKen*/
    private JButton intro;
    /**Botó per a continuar una partida*/
    private JButton continuar;
    /**Botó per a consultar els rànquings*/
    private JButton ranking;
    /**Botó per tancar la sesió*/
    private JButton tancar;
    /**Un dels sis subpanells on es posen els components*/
    private JPanel panel;
    /**Un dels dos subpanells on es posen els components*/
    private JPanel subpanel1;
    /**Un dels dos subpanells on es posen els components*/
    private JPanel subpanel2;
    /**Un dels dos subpanells on es posen els components*/
    private JPanel subpanel3;
    /**Un dels dos subpanells on es posen els components*/
    private JPanel subpanel4;
    /**Un dels dos subpanells on es posen els components*/
    private JPanel subpanel5;
    /**Controlador de presentació*/
    private CtrlPresentacio iCPres;

    /**Creadora de la classe MenuPrincipal
     * @param cpres Controlador de presentació */
    public MenuPrincipal (CtrlPresentacio cpres) {
        iCPres = cpres;
        Color grisc = new Color(228,228,228);
        setBackground(grisc);
        inicialitzarComponents();
    }

    /**Mètode privat que inicialitza tots els components de la vista i els assigna a aquesta*/
    private void inicialitzarComponents() {
        setLayout(new BorderLayout());

        Color grisf = new Color(55,58,64);
        Color grisc = new Color(228,228,228);
        Color tar = new Color(220,95,0);

        nueva = new JButton("Iniciar partida");
        intro = new JButton("Introduir Kenken");
        continuar = new JButton("Continuar partida");
        ranking = new JButton("Rànquings");
        tancar = new JButton("Tancar sessió");

        nueva.setBackground(tar);
        nueva.setForeground(grisc);
        intro.setBackground(tar);
        intro.setForeground(grisc);
        continuar.setBackground(tar);
        continuar.setForeground(grisc);
        ranking.setBackground(tar);
        ranking.setForeground(grisc);
        tancar.setBackground(grisf);
        tancar.setForeground(grisc);

        nueva.setFont(new Font("Microsoft YaHei",Font.PLAIN,20));
        intro.setFont(new Font("Microsoft YaHei",Font.PLAIN,20));
        continuar.setFont(new Font("Microsoft YaHei",Font.PLAIN,20));
        ranking.setFont(new Font("Microsoft YaHei",Font.PLAIN,20));
        tancar.setFont(new Font("Microsoft YaHei",Font.PLAIN,20));

        nueva.setPreferredSize(new Dimension(250, 40));
        intro.setPreferredSize(new Dimension(250, 40));
        continuar.setPreferredSize(new Dimension(250, 40));
        ranking.setPreferredSize(new Dimension(250, 40));
        tancar.setPreferredSize(new Dimension(250, 40));

        nueva.setToolTipText("<html>Inicia una nova partida a<br>partir d'un KenKen aleatori<br>o d'un KenKen guardat</html>");
        intro.setToolTipText("<html>Importa un KenKen a través<br>d'un fitxer o manualment,<br>verifica si és vàlid i guarda'l<br>per jugar-lo més tard</html>");
        continuar.setToolTipText("<html>Reanudar una partida guardada<br></html>");
        ranking.setToolTipText("<html>Consulta els rankings segons<br>la dificultat del KenKen</html>");
        tancar.setToolTipText("<html>Tancar la sessió actual</html>");

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(5, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
        subpanel1 = new JPanel();
        subpanel1.setOpaque(false);
        subpanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        subpanel2 = new JPanel();
        subpanel2.setOpaque(false);
        subpanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        subpanel3 = new JPanel();
        subpanel3.setOpaque(false);
        subpanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        subpanel4 = new JPanel();
        subpanel4.setOpaque(false);
        subpanel4.setLayout(new FlowLayout(FlowLayout.CENTER));
        subpanel5 = new JPanel();
        subpanel5.setOpaque(false);
        subpanel5.setLayout(new FlowLayout(FlowLayout.CENTER));

        subpanel1.add(nueva);
        subpanel2.add(intro);
        subpanel3.add(continuar);
        subpanel4.add(ranking);
        subpanel5.add(tancar);

        panel.add(subpanel1);
        panel.add(subpanel2);
        panel.add(subpanel3);
        panel.add(subpanel4);
        panel.add(subpanel5);

        ListenerBotonN Lnueva = new ListenerBotonN();
        nueva.addActionListener(Lnueva);
        ListenerBotonI Lintro = new ListenerBotonI();
        intro.addActionListener(Lintro);
        ListenerBotonC Lcontinuar = new ListenerBotonC();
        continuar.addActionListener(Lcontinuar);
        ListenerBotonR Lranking = new ListenerBotonR();
        ranking.addActionListener(Lranking);
        ListenerBotonT Ltancar = new ListenerBotonT();
        tancar.addActionListener(Ltancar);

        add(panel, BorderLayout.CENTER);
    }

    /**Classe per al Listener del botó per a jugar una nova partida*/
    private class ListenerBotonN implements ActionListener {
        /**Mètode que crida al controlador de presentació per a canviar de la vista actual a EscullMetode
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.escullMetode();
        }
    }

    /**Classe per al Listener del botó per a introduir un kenken*/
    private class ListenerBotonI implements ActionListener {
        /**Mètode que crida al controlador de presentació per a canviar de la vista actual a IntroduirKenKen
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.introKenKen();
        }
    }

    /**Classe per al Listener del botó per a continuar una partida*/
    private class ListenerBotonC implements ActionListener {
        /**A no ser que hi hagi una única partida o cap, aquest mètode que crida al controlador de presentació per a canviar de la vista actual a SeleccioContinuar
         * Si no hi ha cap partida activa es mostrarà un missatge d'error informatiu, en canvi si n'hi ha una es mostrarà un missatge de confirmació sobre que es jugarà aquella partida
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            if (iCPres.partidesJugadorSize() == 0) iCPres.Missatge(2, "No hi ha partides", "No hi ha cap partida guardada");
            else if (iCPres.partidesJugadorSize() == 1) {
                if (iCPres.Missatge(3, "Només una partida", "Només hi ha una partida en curs, vols reprendre-la?")) {
                    iCPres.jugaKenKen(-1,0);
                }
            }
            else {
                iCPres.seleccionaContinuar();
            }
        }
    }

    /**Classe per al Listener del botó per a consultar els rànquings*/
    private class ListenerBotonR implements ActionListener {
        /**Mètode que crida al controlador de presentació per a canviar de la vista actual a VistaRankings
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.vistaRankings();
        }
    }

    /**Classe per al Listener del botó per a tancar sessió*/
    private class ListenerBotonT implements ActionListener {
        /**Mètode que mostra un missatge de confirmació sobre tancar sessió. Si es respon afirmativament es tanca la sessió i es retorna a la vista IniciSessio
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
                if (iCPres.Missatge(3, "Confirmar tancar sessió", "Estàs segur que vols tancar la sessió?")) {
                    iCPres.tancaSessio();
                }
        }
    }
}
