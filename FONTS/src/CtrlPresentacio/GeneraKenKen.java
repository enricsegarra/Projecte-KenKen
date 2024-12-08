package CtrlPresentacio;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**Classe que hereta de JPanel (és un panell) on s'escull la dificultat per jugar una partida amb un kenken generat aleatòriament*/
public class GeneraKenKen extends JPanel {
    /**Menú desplegable amb les diferents dificultats seleccionables*/
    private JComboBox<Integer> menuDesplegable;
    /**Etiqueta amb l'informació necessària per a l'usuari*/
    private JLabel info;
    /**Botó per a iniciar una partida amb un kenken aleatori de la dificultat seleccionada*/
    private JButton generar;
    /**Botó per a cancel·lar l'iniciació d'una partida amb un kenken aleatori*/
    private JButton cancelar;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel npanel;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel mpanel;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel bpanel;
    /**Controlador de presentació*/
    private CtrlPresentacio iCPres;

    /**Creadora de la classe GeneraKenKen
     * @param CP Controlador de presentació*/
    public GeneraKenKen(CtrlPresentacio CP) {
        iCPres = CP;
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

        info = new JLabel("Selecciona la dificultat del KenKen a generar");
        info.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        info.setHorizontalAlignment(SwingConstants.CENTER);

        menuDesplegable = new JComboBox<>();
        for (int i = 3; i <= 9; ++i) {
            menuDesplegable.addItem(i);
        }

        generar = new JButton("Generar");
        generar.setBackground(tar);
        generar.setForeground(grisc);
        generar.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        ListenerBotonGen Lgen = new ListenerBotonGen();
        generar.addActionListener(Lgen);

        cancelar = new JButton("Cancel·lar");
        cancelar.setBackground(grisf);
        cancelar.setForeground(grisc);
        cancelar.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        ListenerBotonCan Lcan = new ListenerBotonCan();
        cancelar.addActionListener(Lcan);

        npanel = new JPanel();
        npanel.setOpaque(false);
        npanel.add(info);

        mpanel = new JPanel();
        mpanel.setOpaque(false);
        mpanel.add(menuDesplegable);

        bpanel = new JPanel();
        bpanel.setOpaque(false);
        bpanel.add(generar);
        bpanel.add(cancelar);

        add(npanel, BorderLayout.NORTH);
        add(mpanel, BorderLayout.CENTER);
        add(bpanel, BorderLayout.SOUTH);
    }

    /**Classe per al Listener del botó de generar un kenken aleatòri i començar la partida*/
    private class ListenerBotonGen implements ActionListener {
        /**Mètode que crida al controlador de presentació per a generar un kenken aleatori del tamany desitjat i iniciar la partida (cambiant a la vista JugarPartida)
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            int tam = (int) menuDesplegable.getSelectedItem();
            iCPres.generaKenKen(tam);
        }
    }

    /**Classe per al Listener del botó de cancel·lar*/
    private class ListenerBotonCan implements ActionListener {
        /**Mètode que crida al controlador de presentació per cambiar de la vista actual a EscullMetode
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.tornarEM();
        }
    }

}
