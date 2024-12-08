package CtrlPresentacio;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**Classe que hereta de JPanel (és un panell) on s'introdueix, ja sigui manualment o per fitxer, un kenken per a crear-lo, validar-lo i (si es deistja) guardar-lo*/
public class IntroduirKenKen extends JPanel {
    /**Àrea de text per a introduir el kenken*/
    private JTextArea txt;
    /**Etiqueta amb l'informació necessària per a l'usuari*/
    private JLabel lbl;
    /**Botó per a crear i validar (i si es vol guardar) el kenken introduït*/
    private JButton guardar;
    /**Botó per retornar al menú principal*/
    private JButton enrere;
    /**Botó per a seleccionar un fitxer d'entrada de dades*/
    private JButton fitxer;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel sud;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel bpanel;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel epanel;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel lpanel;
    /**Un dels cinc subpanells on es posen els components*/
    private JPanel tpanel;
    /**Vora per delimitar els components necessaris*/
    private Border borde;
    /**Controlador de presentació*/
    private CtrlPresentacio iCPres;

    /**Creadora de la classe IntroduirKenKen
     * @param pCpres Controlador de presentació */
    public IntroduirKenKen (CtrlPresentacio pCpres) {
        iCPres = pCpres;
        Color grisc = new Color(228,228,228);
        setBackground(grisc);
        inicialitzarComponents();
    }

    /**Mètode privat que inicialitza tots els components de la vista i els assigna a aquesta*/
    private void inicialitzarComponents() {
        setLayout(new BorderLayout());

        Color tar = new Color(220,95,0);
        Color grisf = new Color(55,58,64);
        Color grisc = new Color(228,228,228);

        guardar = new JButton("Validar");
        guardar.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        guardar.setBackground(tar);
        guardar.setForeground(grisc);

        enrere = new JButton("Enrere");
        enrere.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        enrere.setBackground(grisf);
        enrere.setForeground(grisc);

        fitxer = new JButton("Selec. Fitxer");
        fitxer.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        fitxer.setBackground(grisf);
        fitxer.setForeground(grisc);

        ListenerBotonE Lenrere = new ListenerBotonE();
        enrere.addActionListener(Lenrere);
        ListenerBotonG Lguardar = new ListenerBotonG();
        guardar.addActionListener(Lguardar);
        ListenerBotonF Lfitxer = new ListenerBotonF();
        fitxer.addActionListener(Lfitxer);
        sud = new JPanel(new GridLayout(1,2));
        sud.setOpaque(false);
        bpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bpanel.setOpaque(false);
        epanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        epanel.setOpaque(false);
        bpanel.add(guardar);
        bpanel.add(fitxer);
        epanel.add(enrere);
        sud.add(bpanel);
        sud.add(epanel);

        lpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lpanel.setOpaque(false);
        lbl = new JLabel("Introdueix el KenKen: ");
        lbl.setForeground(grisf);
        lbl.setFont(new Font("Arial", Font.PLAIN, 25));
        lpanel.add(lbl);
        lbl.setBorder(BorderFactory.createEmptyBorder(40,40,0,0));
        
        tpanel = new JPanel();
        tpanel.setOpaque(false);
        txt = new JTextArea(20,40);
        txt.setSelectedTextColor(grisf);
        txt.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        borde = BorderFactory.createLineBorder(Color.BLACK);
        txt.setBorder(borde);
        JScrollPane scrollPane = new JScrollPane(txt);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tpanel.add(scrollPane);
        tpanel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        add(tpanel, BorderLayout.CENTER);
        add(lpanel, BorderLayout.WEST);
        add(sud, BorderLayout.SOUTH);
    }

    /**Mètode que durant la creació i validació del kenken mostra un missatge informatiu. El booleà b indica si s'ha de guardar el kenken
     * @param b Booleà que indica si s'ha de guardar el kenken o no*/
    public void ACrearElKenKen(boolean b) {
        JLabel label = new JLabel("Verificant KenKen, això pot trigar uns segons...");
        label.setForeground(new Color(55,58,64));

        final JOptionPane pane = new JOptionPane(label, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = new JDialog();
        dialog.setBackground(new Color(228,228,228));
        dialog.setTitle("Verificant KenKen...");
        dialog.setContentPane(pane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        Timer timer = new Timer(10, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    iCPres.creaKenKen(txt.getText(),b);
                    int id = iCPres.quantsKenKens();
                    dialog.dispose();
                    if (b) iCPres.Missatge(2,"Guarda KenKen","S'ha guardat el KenKen amb l'identificador " + id);
                    else iCPres.Missatge(2,"No Guarda KenKen","El KenKen és vàlid i no s'ha guardat");
                }
                catch (Exception e) {
                    dialog.dispose();
                    iCPres.Missatge(1,"Error al validar KenKen", "El KenKen es invàlid: " + e.getMessage());
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    /**Classe per al Listener del botó de tornar enrere*/
    private class ListenerBotonE implements ActionListener {
        /**Mètode que crida al controlador de presentació per cambiar de la vista actual a MenuPrincipal
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            iCPres.tornarMP();
        }
    }

    /**Classe per al Listener del botó de crear i validar el kenken*/
    private class ListenerBotonG implements ActionListener {
        /**Mètode que mostra un missatge de confirmació sobre guardar el kenken. Es crida al controlador de presentació per a crear i validar (i guardar si es desitja) el kenken. 
         * Si el kenken es de mida 8x8 o 9x9 en comptes de cridar directament al controlador de presentacio cridarà al mètode ACrearElKenKen(boolean b)
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            try {
                if (iCPres.Missatge(3,"Guardar KenKen", "En el cas que sigui vàlid, vols guardar el KenKen?")) {
                    if (txt.getText().charAt(0) == '8' || txt.getText().charAt(0) == '9') {
                        ACrearElKenKen(true);
                    }
                    else {
                        iCPres.creaKenKen(txt.getText(),true);
                        int id = iCPres.quantsKenKens() - 1;
                        iCPres.Missatge(2,"Guarda KenKen","S'ha guardat el KenKen amb l'identificador " + id);
                    }
                }
                else {
                    if (txt.getText().charAt(0) == '8' || txt.getText().charAt(0) == '9') {
                        ACrearElKenKen(false);
                    }
                    else {
                        iCPres.creaKenKen(txt.getText(),false);
                        iCPres.Missatge(2,"No Guarda KenKen","El KenKen és vàlid i no s'ha guardat");
                    }
                }
            }
            catch (Exception e) {
                iCPres.Missatge(1,"Error al validar KenKen", "El KenKen es invàlid: " + e.getMessage());
            }
        }
    }

    /**Classe per al Listener del botó de seleccionar un fitxer d'entrada*/
    private class ListenerBotonF implements ActionListener {
        /**Mètode que obre un gestor de fitxers (amb un filtre per a mostrar només .txt) per a seleccionar un fitxer d'entrada. Copia el contingut del fitxer seleccionat a l'àrea de text
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            JFileChooser filec = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
            filec.setFileFilter(filter);
            int result = filec.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = filec.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    txt.setText("");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        txt.append(line + "\n");
                    }
                }
                catch (IOException e) {
                    iCPres.Missatge(1, "Error d'arxiu", "Error al llegir l'arxiu: " + e.getMessage());
                }
            }
        }
    }
}
