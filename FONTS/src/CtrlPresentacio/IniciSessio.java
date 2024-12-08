package CtrlPresentacio;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**Classe que hereta de JPanel (és un panell) on es pot gestionar els perfils*/
public class IniciSessio extends JPanel {
    /**Camp de text per a introduir el nom d'usuari*/
    private JTextField usernameField;
    /**Camp de text per a introduir la contrasenya*/
    private JTextField passwordField;
    /**Etiqueta que indica que el camp de text és per al nom d'usuari*/
    private JLabel usernameLbl;
    /**Etiqueta que indica que el camp de text és per a la contrasenya*/
    private JLabel passwordLbl;
    /**Botó per a iniciar sessió amb el username i la contrasenya introduïts*/
    private JButton login;
    /**Botó per a crear un perfil amb el username i la contrasenya introduïts*/
    private JButton crea;
    /**Botó per a borrar un perfil amb el username i la contrasenya introduïts*/
    private JButton borra;
    /**Un dels quatre subpanells on es posen els components*/
    private JPanel panel;
    /**Un dels quatre subpanells on es posen els components*/
    private JPanel upanel;
    /**Un dels quatre subpanells on es posen els components*/
    private JPanel ppanel;
    /**Un dels quatre subpanells on es posen els components*/
    private JPanel bpanel;
    /**Controlador de presentació*/
    private CtrlPresentacio iCPres;

    /**Creadora de la classe IniciSessio
     * @param pCpres Control de presentacio */
    public IniciSessio (CtrlPresentacio pCpres) {
        iCPres = pCpres;
        Color grisc = new Color(228,228,228);
        setBackground(grisc);
        inicialitzarComponents();
    }

    /**Mètode privat que inicialitza tots els components de la vista i els assigna a aquesta*/
    private void inicialitzarComponents() {
        setLayout(new BorderLayout());

        Color grisf = new Color(55,58,64);      
        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(3,2));

        upanel = new JPanel();
        upanel.setOpaque(false);
        upanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        ppanel = new JPanel();
        ppanel.setOpaque(false);
        ppanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        usernameLbl = new JLabel("Usuari: ");
        usernameLbl.setFont(new Font("Microsoft YaHei",Font.BOLD,13));
        usernameLbl.setForeground(grisf);
        passwordLbl = new JLabel("Contrasenya:");
        passwordLbl.setFont(new Font("Microsoft YaHei",Font.BOLD,13));
        passwordLbl.setForeground(grisf);
        usernameField = new JTextField(30);
        usernameField.setFont(new Font("Microsoft YaHei",Font.PLAIN,12));
        usernameField.setForeground(grisf);
        passwordField = new JPasswordField(30);
        passwordField.setFont(new Font("Microsoft YaHei",Font.PLAIN,12));
        passwordField.setForeground(grisf);

        upanel.add(usernameLbl);
        upanel.add(usernameField);
        ppanel.add(passwordLbl);
        ppanel.add(passwordField);

        Color tar = new Color(220,95,0);
        Color grisc = new Color(228,228,228);

        login = new JButton("Iniciar sessió");
        login.setFont(new Font("Microsoft YaHei",Font.BOLD,11));
        login.setBackground(tar);
        login.setForeground(grisc);
        ListenerBotonIni Liniciar = new ListenerBotonIni();
        login.addActionListener(Liniciar);

        crea = new JButton(" Crear perfil ");
        crea.setFont(new Font("Microsoft YaHei",Font.BOLD,11));
        crea.setBackground(tar);
        crea.setForeground(grisc);
        ListenerBotonCrea Lcrear = new ListenerBotonCrea();
        crea.addActionListener(Lcrear);

        borra = new JButton("Esborrar perfil");
        borra.setFont(new Font("Microsoft YaHei",Font.BOLD,11));
        borra.setBackground(grisf);
        borra.setForeground(grisc);
        ListenerBotonBorra Lborrar = new ListenerBotonBorra();
        borra.addActionListener(Lborrar);

        bpanel = new JPanel();
        bpanel.setOpaque(false);
        bpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bpanel.add(borra);
        bpanel.add(crea);
        bpanel.add(login);

        panel.add(upanel);
        panel.add(ppanel);
        panel.add(bpanel);
        add(panel);
    }

    /**Mètode que mostra el string S2 com a missatge d'error amb el string S1 com a títol de la finestra
     * @param S1 Títol de la finestra
     * @param S2 Missatge que es mostrarà */
    public static void MissatgeError(String S1, String S2) {
        Color grisf = new Color(55,58,64);
        Color grisc = new Color(228,228,228);
        JLabel missatge = new JLabel(S2);
        missatge.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        missatge.setForeground(grisf);
        JOptionPane optPane = new JOptionPane(missatge,JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = optPane.createDialog(S1);
        for (Component comp : optPane.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component compInt : ((JPanel) comp).getComponents()) {
                    if (compInt instanceof JButton) {
                        JButton button = (JButton) compInt;
                        button.setBackground(grisf);
                        button.setForeground(grisc);
                        button.setFont(new Font("Microsoft YaHei",Font.BOLD,11));
                        button.setText(" Acceptar ");
                    }
                }
            }
        }
        dialog.setBackground(grisc);
        dialog.setVisible(true);
    }

    /**Mètode que mostra el string S2 com a missatge informatiu amb el string S1 com a títol de la finestra
     * @param S1 Títol de la finestra
     * @param S2 Missatge que es mostrarà */
    public static void MissatgeInfo(String S1, String S2) {
        Color grisf = new Color(55,58,64);
        Color grisc = new Color(228,228,228);
        JLabel missatge = new JLabel(S2);
        missatge.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        missatge.setForeground(grisf);
        JOptionPane optPane = new JOptionPane(missatge,JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = optPane.createDialog(S1);
        for (Component comp : optPane.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component compInt : ((JPanel) comp).getComponents()) {
                    if (compInt instanceof JButton) {
                        JButton button = (JButton) compInt;
                        button.setBackground(grisf);
                        button.setForeground(grisc);
                        button.setFont(new Font("Microsoft YaHei",Font.BOLD,11));
                        button.setText(" Acceptar ");
                    }
                }
            }
        }
        dialog.setBackground(grisc);
        dialog.setVisible(true);
    }

    /**Mètode que crea un missatge de confirmació amb el string S2 i amb el string S1 com a títol de la finestra. Si es confirma retorna true, altrament retorna false
     * @param S1 Títol de la finestra
     * @param S2 Missatge que es mostrarà */
    public static boolean Confirmacio(String S1, String S2) {
        Color grisf = new Color(55,58,64);
        Color grisc = new Color(228,228,228);
        JLabel missatge = new JLabel(S2);
        missatge.setFont(new Font("Microsoft YaHei",Font.PLAIN,13));
        missatge.setForeground(grisf);
        JOptionPane optPane = new JOptionPane(missatge,JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
        JDialog dialog = optPane.createDialog(S1);
        boolean b = false;
        for (Component comp : optPane.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component compInt : ((JPanel) comp).getComponents()) {
                    if (compInt instanceof JButton) {
                        JButton button = (JButton) compInt;
                        if (!b) { 
                            button.setBackground(new Color(220,95,0));
                            button.setText("Sí");
                            b = true;
                        }
                        else {
                            button.setBackground(grisf);
                            button.setText(" No ");
                        }
                        button.setForeground(grisc);
                        button.setFont(new Font("Microsoft YaHei",Font.BOLD,11));
                    }
                }
            }
        }
        dialog.setBackground(grisc);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        try {
            Integer opcion = (Integer) optPane.getValue();
            if (opcion == JOptionPane.YES_OPTION) {
                dialog.dispose();
                if (S2 == "Estàs segur que vols esborrar aquest perfil?") IniciSessio.MissatgeInfo("Esborrar Perfil","S'ha esborrat el perfil correctament");
                return true;

            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**Classe per al Listener del botó d'iniciar sessió*/
    private class ListenerBotonIni implements ActionListener {
        /**Mètode que (en cas que cap del dos camps de text estiguin buits) crida al mètode del controlador de presentació d'iniciar sessió
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                MissatgeError("Error: usuari o contrasenya invàlids","Error a l'iniciar sessió");
            }
            else iCPres.iniciarSessio(usernameField.getText(), passwordField.getText());
        }
    }

    /**Classe per al Listener del botó de crear un perfil*/
    private class ListenerBotonCrea implements ActionListener {
        /**Mètode que (en cas que cap del dos camps de text estiguin buits) crida al mètode del controlador de presentació de crear un perfil
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                MissatgeError("Error: usuari o contrasenya invàlids","Error al crear perfil");
            }
            else iCPres.crea(usernameField.getText(), passwordField.getText());
        }
    }

    /**Classe per al Listener del botó de borrar un perfil*/
    private class ListenerBotonBorra implements ActionListener {
        /**Mètode que (en cas que cap del dos camps de text estiguin buits) crida al mètode del controlador de presentació de borrar un perfil
         * @param event Acció que es realitza sobre el botó */
        public void actionPerformed (ActionEvent event) {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                MissatgeError("Error: usuari o contrasenya invàlids","Error al esborrar perfil");
            }
            else {
                iCPres.borra(usernameField.getText(), passwordField.getText());
            }
        }
    }

}
