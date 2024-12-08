package Domini;

/**Classe Divisio, la qual hereta de la classe abstracta Operacio*/
public class Divisio extends Operacio {

    /** Creadora de classe*/
    @Override
    public void Operacio() {

    }

    /** Comprova que la divisió dels valors del vector de caselles de la Zona z sigui igual al resultat de z
     * @param x Fila de la casella
     * @param y Columna de la casella 
     * @param v Valor a comprovar
     * @param z Zona a modificar */
    @Override
    public boolean posValida(int x, int y, int v, Zona z) {
        int v2;
        boolean b = false;
        for (int i = 0; i < 2; ++i) {
            if (!z.esCasella(z.getCasella(i), x, y)) {
                v2 = z.getValorCasella(z.getCasella(i));
                if (v2 != 0) {
                    int total = z.getTotal();
                    if (total == (v2 / v) || total == (v / v2)) {
                         b= true;
                    }
                }
                else b = true;
            }
        }
        if(b){
            for (int i = 0; i < 2; ++i) {
                if (z.esCasella(z.getCasella(i), x, y)) {
                    z.setValor(z.getCasella(i), v);
                        return true;
                }
            }
        }        
        return false;
    }
}
