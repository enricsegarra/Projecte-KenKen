package Main;

import CtrlPresentacio.CtrlPresentacio;

/**Classe amb el mètode main del programa*/
public class ProgramaPrincipal {

    /**Mètode main del programa*/
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater (
            new Runnable() {
                public void run() {
                    CtrlPresentacio CPres = new CtrlPresentacio();
                    CPres.inicialitzarPresentacio();
                }
            }
        );
    }
}
