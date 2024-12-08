package Domini;

/**Classe Potencia, la qual hereta de la classe abstracta Operacio*/
public class Potencia extends Operacio {

    /** Creadora de classe*/
    @Override
    public void Operacio() {

    }

    /** Comprova que la potència dels valors del vector de caselles de la Zona z sigui igual al resultat de z
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
                    if (total == pot(v, v2) || total == pot(v2, v)) {
                        b = true;
                    }
                }
                b = true;

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
    
    /** Calcula la potència dels dos valors entrats per paràmetre, sent a la base, i b l'exponent
     * @param a Base de la potència
     * @param b Exponent de la potència */
    public int pot(int a, int b) {
        int result = a;
        for (int i = 1; i < b; i++) {
            result *= a;
        }
        return result;
    }
}
