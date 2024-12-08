package Domini;

/**Classe abstracta Operacio*/
public abstract class Operacio {
    
    /** Creadora de la classe*/
    public abstract void Operacio();
    
    /** Operació abstracta a ser implementada
     * @param x Fila de la casella
     * @param y Columna de la casella 
     * @param v Valor a comprovar
     * @param z Zona a modificar */
    public abstract boolean posValida(int x, int y, int v, Zona z);
}
