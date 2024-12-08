package CtrlPresentacio;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

/**Classe que hereta de JPanel (és un panell) on es selecciona la partida que es vol reprendre*/
public class VistaRankings extends JPanel {
    /**Àrea de text on es mostra el rànquing de la dificultat seleccionada*/
    private JTextArea txt;
    /**Etiqueta amb l'informació necessària per a l'usuari*/
    private JLabel lbl;
    /**Botó per retornar al menú principal*/
    private JButton enrere;
    /**Llista desplegable amb les diferents dificultats possibles d'una partida*/
    private JComboBox<String> menuDificultat;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel bpanel;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel lpanel;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel tpanel;
    /**Barra de desplaçament per a poder visualitzar tots els elements dels rànquings*/
    private JScrollPane scrollPane;
    /**Vora per delimitar els components necessaris*/
    private Border borde;
    /**Controlador de presentació*/
    private CtrlPresentacio iCPres;

    /**Creadora de la classe VistaRankings
     * @param pCpres Controlador de presentació */
    public VistaRankings (CtrlPresentacio pCpres) {
        iCPres = pCpres;
        Color col = new Color(228,228,228);
        setBackground(col);
        inicialitzarComponents();
    }

    /**Mètode privat que inicialitza tots els components de la vista i els assigna a aquesta*/
    private void inicialitzarComponents() {
        setLayout(new BorderLayout());

        //Color tar = new Color(220,95,0);
        Color grisf = new Color(55,58,64);
        Color grisc = new Color(228,228,228);
        
        enrere = new JButton("Enrere");
        enrere.setBackground(grisf);
        enrere.setForeground(grisc);
        enrere.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        ListenerBotonE Lenrere = new ListenerBotonE();
        enrere.addActionListener(Lenrere);
        bpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bpanel.setOpaque(false);
        bpanel.add(enrere);

        lpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lpanel.setOpaque(false);
        lpanel.setBorder(BorderFactory.createEmptyBorder(40,40,0,0));
        lbl = new JLabel("Dificultat: ");
        lbl.setFont(new Font("Microsoft YaHei",Font.PLAIN,25));
        lpanel.add(lbl);
        menuDificultat = new JComboBox<>();
        for (int i = 3; i <= 9; ++i) {
            menuDificultat.addItem(i + "x" +i);
        }
        ListenerDificultat LDificultat = new ListenerDificultat();
        menuDificultat.addActionListener(LDificultat);
        lpanel.add(menuDificultat);
        
        tpanel = new JPanel();
        tpanel.setOpaque(false);

        tpanel.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
        txt = new JTextArea(15,15);
        txt.setEditable(false);
        txt.setSelectedTextColor(grisf);
        txt.setFont(new Font("Microsoft YaHei",Font.PLAIN,18));
        borde = BorderFactory.createLineBorder(Color.BLACK);
        txt.setBorder(borde);
        scrollPane = new JScrollPane(txt);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tpanel.add(scrollPane);

        txt.setText(iCPres.mostrar_ranking(menuDificultat.getSelectedItem().toString()));
        add(tpanel, BorderLayout.CENTER);
        add(lpanel, BorderLayout.WEST);
        add(bpanel, BorderLayout.SOUTH);
    }

    /**Classe per al Listener del botó de tornar enrere*/
    private class ListenerBotonE implements ActionListener {
        /**Mètode que crida al controlador de presentació per cambiar de la vista actual a MenuPrincipal
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.tornarMP();
        }
    }

    /**Classe per al seleccionador de dificultat*/
    private class ListenerDificultat implements ActionListener {
        /**Mètode que mostra a l'àrea de text el rànquing seleccionat a la llista desplegable
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            String r = iCPres.mostrar_ranking(menuDificultat.getSelectedItem().toString());
            txt.setText(r);
        }
    }
}
