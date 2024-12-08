package Domini;

/**Classe Multiplicacio, la qual hereta de la classe abstracta Operacio*/
public class Multiplicacio extends Operacio {

    /** Creadora de classe*/
    @Override
    public void Operacio() {

    }

    /** Comprova que la multiplicació dels valors del vector de caselles de la Zona z sigui igual al resultat de z
     * @param x Fila de la casella
     * @param y Columna de la casella 
     * @param v Valor a comprovar
     * @param z Zona a modificar */
    @Override
    public boolean posValida(int x, int y, int v, Zona z) {
        int tot = 1;
        int buida = 0;
        for (int i = 0; i < z.consultaTamanyCaselles(); ++i) {
            if (z.esCasella(z.getCasella(i), x, y)) {
                tot = tot * v;
            } else {
                int valor = z.getValorCasella(z.getCasella(i));
                if (valor == 0) buida++;
                else tot = tot * valor;
            }
        }
        int total = z.getTotal();
        if (total == tot || (total > tot && buida > 0)) {
            for (int i = 0; i < z.consultaTamanyCaselles(); ++i) {
                if (z.esCasella(z.getCasella(i), x, y)) {
                    z.setValor(z.getCasella(i), v);
                    return true;
                }
            }
        }
        return false;
    }
}
