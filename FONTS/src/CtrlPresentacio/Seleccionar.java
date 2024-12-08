package CtrlPresentacio;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

/**Classe que hereta de JPanel (és un panell) on es selecciona la partida que es vol reprendre*/
public class Seleccionar extends JPanel {
    /**Etiqueta amb l'informació necessària per a l'usuari*/
    private JLabel lbl;
    /**Botó per retornar a escollir un mètode per a iniciar una partida*/
    private JButton enrere;
    /**Botó per iniciar una partida amb el kenken seleccionat*/
    private JButton jugar;
    /**Àrea de text on s'introdueix el identificador del kenken amb el que es desitja iniciar una partida*/
    private JTextField txt;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel bpanel;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel npanel;
    /**Un dels tres subpanells on es posen els components*/
    private JPanel cpanel;
    /**Component per aplicar el filtre a l'àrea de text*/
    private PlainDocument doc;
    /**Controlador de presentació*/
    private CtrlPresentacio iCPres;

    /**Creadora de la classe Seleccionar
     * @param pCpres Controlador de presentació */
    public Seleccionar (CtrlPresentacio pCpres) {
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
        
        enrere = new JButton("Enrere");
        enrere.setBackground(grisf);
        enrere.setForeground(grisc);
        enrere.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        ListenerBotonE Lenrere = new ListenerBotonE();
        enrere.addActionListener(Lenrere);
        jugar = new JButton("Jugar");
        jugar.setBackground(tar);
        jugar.setForeground(grisc);
        jugar.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        ListenerBotonJ Ljugar = new ListenerBotonJ();
        enrere.addActionListener(Lenrere);
        jugar.addActionListener(Ljugar);
        bpanel = new JPanel();
        bpanel.setOpaque(false);
        bpanel.add(jugar);
        bpanel.add(enrere);
        
        lbl = new JLabel("Introdueix l'id del KenKen que vols jugar [0 .. " + (iCPres.quantsKenKens()-1) + "]:");
        lbl.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        npanel = new JPanel();
        npanel.setOpaque(false);
        npanel.add(lbl);

        cpanel = new JPanel();
        cpanel.setOpaque(false);
        txt = new JTextField(2);
        txt.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        
        cpanel.add(txt);

        doc = (PlainDocument) txt.getDocument();
        doc.setDocumentFilter(new MyIntFilter2(iCPres.quantsKenKens()-1));

        add(npanel, BorderLayout.NORTH);
        add(cpanel, BorderLayout.CENTER);
        add(bpanel, BorderLayout.SOUTH);
    }

    /**Classe per al Listener del botó de tornar enrere*/
    private class ListenerBotonE implements ActionListener {
        /**Mètode que crida al controlador de presentació per cambiar de la vista actual a EscullMetode
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.tornarEM();
        }
    }

    /**Classe per al Listener del botó de jugar*/
    private class ListenerBotonJ implements ActionListener {
        /**Aquest mètode inicia la partida amb l'identificador de kenken que s'ha introduït, canviant la vista actual (amb el controlador de presentació) a JugarPartida. Si no s'ha seleccionat cap kenken mostra un missatge d'error
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            String s = txt.getText();
            if (s.isEmpty()) iCPres.Missatge(1, "Error selecció", "Selecciona un KenKen");
            else iCPres.jugaKenKen(Integer.parseInt(s),-1);;
        }
    }

    /**Classe anidada que actua com a filtre de l'àrea de text, on només permet escriure un nombre, el qual ha d'identificar un kenken que es trobi persistència*/
    class MyIntFilter2 extends DocumentFilter { 
        /**Identificador de kenken més gran actualment */
        private int max;
    
        /**Creadora de la classe MyIntFilter2
         * @param max Identificador de kenken més gran actualment */
        public MyIntFilter2(int max) {
            this.max = max;
        }
        
        /**Mètode privat que comprova si el valor introduït és un nombre entre 0 i l'identificador de kenken més gran. En cas afirmatiu retorna true, altrament es retorna false
         * @param text Text introduit a l'àrea de text */
        private boolean test(String text) {
           try {
              int n = Integer.parseInt(text);
              if (n >= 0 && n <= max) return true;
              return false;
           } catch (NumberFormatException e) {
              return false;
           }
        }
     
        /**Mètode que es crida cada cop que s'introdueix un valor a l'àrea de text. Només deixa cambiar el valor de la casella si es cumpleixen les condicions del mètode test(String text)*/
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
              AttributeSet attrs) throws BadLocationException {
     
           Document doc = fb.getDocument();
           StringBuilder sb = new StringBuilder();
           sb.append(doc.getText(0, doc.getLength()));
           sb.replace(offset, offset + length, text);
     
           if (test(sb.toString())) {
              super.replace(fb, offset, length, text, attrs);
           }
     
        }
     }
}