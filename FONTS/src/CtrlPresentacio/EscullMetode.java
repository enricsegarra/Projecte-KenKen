package CtrlPresentacio;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**Classe que hereta de JPanel (és un panell) on s'escull un dels dos mètodes per iniciar una partida (generar un kenken aleatori o seleccionar un de persistència)*/
public class EscullMetode extends JPanel {
    /**Botó per a seleccionar generar un kenken aleatori*/
    private JButton generar;
    /**Botó per a seleccionar agafar un kenken de persistència*/
    private JButton creat;
    /**Botó per retornar al menú principal*/
    private JButton enrere;
    /**Etiqueta amb l'informació necessària per a l'usuari*/
    private JLabel lbl;
    /**Un dels dos subpanells on es posen els components*/
    private JPanel panel;
    /**Un dels dos subpanells on es posen els components*/
    private JPanel epanel;
    /**Controlador de presentació*/
    private CtrlPresentacio iCPres;

    /**Creadora de la classe EscullMetode 
     * @param pCpres Controlador de presentació */
    public EscullMetode (CtrlPresentacio pCpres) {
        iCPres = pCpres;
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

        lbl = new JLabel("Vols seleccionar un KenKen creat o generar un de nou?");
        lbl.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        lbl.setForeground(grisf);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        generar = new JButton("Generar");
        generar.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        generar.setBackground(tar);
        generar.setForeground(grisc);

        creat = new JButton("Seleccionar");
        creat.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        creat.setBackground(tar);
        creat.setForeground(grisc);

        enrere = new JButton("Enrere");
        enrere.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        enrere.setBackground(grisf);
        enrere.setForeground(grisc);

        panel = new JPanel();
        panel.setOpaque(false);

        JPanel aux = new JPanel();
        aux.setOpaque(false);
        aux.add(lbl);

        add(aux, BorderLayout.NORTH);
        panel.add(generar);
        panel.add(creat);
        add(panel, BorderLayout.CENTER);

        epanel = new JPanel();
        epanel.setOpaque(false);
        epanel.add(enrere);
        epanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(epanel, BorderLayout.SOUTH);

        ListenerBotonG Lnueva = new ListenerBotonG();
        generar.addActionListener(Lnueva);
        ListenerBotonC Lintro = new ListenerBotonC();
        creat.addActionListener(Lintro);
        ListenerBotonE Lenrere = new ListenerBotonE();
        enrere.addActionListener(Lenrere);

    }

    /**Classe per al Listener del botó de generar un kenken aleatòri*/
    private class ListenerBotonG implements ActionListener {
        /**Mètode que crida al controlador de presentació per a canviar de la vista actual a la de GeneraKenKen
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.generaAleatori();
        }
    }

    /**Classe per al Listener del botó de seleccionar un kenken ja creat*/
    private class ListenerBotonC implements ActionListener {
        /**Mètode que si no hi ha cap kenken a persistència mostra un missatge d'avís, que si només n'hi ha un mostra un missatge preguntat si es vol jugar a aquell
         * (en cas afirmatiu cambia de vista a Jugar partida) i si n'hi ha més d'un crida al controlador de persistència per a cambiar la vista actual per Seleccionar
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            if(iCPres.quantsKenKens() == 0) iCPres.Missatge(2,"No hi ha KenKens", "Encara no sha creat cap KenKen");
            else if (iCPres.quantsKenKens() == 1) {
                if (iCPres.Missatge(3,"Només un KenKen","Només hi ha un KenKen creat, vols jugar aquest?")) {
                    iCPres.jugaKenKen(0,-1);
                }
            }
            else {
                iCPres.seleccionaKenKen();
            }
        }
    }

    /**Classe per al Listener del botó de tornar enrere*/
    private class ListenerBotonE implements ActionListener {
        /**Mètode que crida al controlador de presentació per cambiar de la vista actual a MenuPrincipal
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.tornarMP();
        }
    }

}
