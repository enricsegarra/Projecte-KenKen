package Domini;

/**Classe casella*/
public class Casella {
    /**Enter amb la fila de la casella*/
    private int x;
    /**Enter amb la columna de la casella*/
    private int y;
    /**Enter amb el valor de la casella*/
    private int valor;
    /**Enter amb l'identificador de la zona que conté la casella*/
    private int idz;
    /**Booleà que identifica si la casella ha estat predefinida durant la creació del kenken (true) o no (false)*/
    private boolean pre;

    /** Creadora d’una casella buida, x, y, idz s’inicialitzen a -1 per detectar que la casella no és vàlida. Valor és 0 per defecte per qualsevol casella buida, i pre és fals per defecte*/
    public Casella() {
        idz = x = y = -1;
        valor = 0;
        pre = false;
    }

    /** Creadora de casella vàlida. S’assigna un idz per identificar la Zona de la casella i s’assignen els x i y passats per paràmetre. El valor de la casella és 0 per defecte i pre és fals també
     * @param idz Identificador de la zona on pertany la casella
     * @param x Fila de la casella
     * @param y Columna de la casella */
    public Casella(int idz, int x, int y) {
        this.x = x;
        this.y = y;
        this.valor = 0; // Per defecte, el valor inicial es 0
        this.idz = idz;
        pre = false;
    }

    /** Retorna true si la x i y de la casella coincideixen amb els passats per paràmetre
     * @param x Fila de la casella
     * @param y Columna de la casella */
    public boolean esCasella(int x,int y){
        return (x == this.x && y == this.y);
    }
    
    /** Consultora que retorna si la casella té un valor predefinit (booleà pre) */
    public boolean getPre() {
        return pre;
    }
    
    /** Modificadora del booleà que indica si la casella té un valor predefinit */
    public void setPre() {
        if (!pre) pre = true;
        else pre = false;
    }

    /** Consultora que retorna el valor de la casella */
    public int consultaValor() {
        return valor;
    }

    /** Consultora que retorna la fila de la casella */
    public int getX() {
        return x;
    }

    /** Modificadora que assigna una fila a la casella 
     * @param x Fila a assignar a la casella */
    public void setX(int x) {
        this.x = x;
    }

    /** Consultora que retorna la columna de la casella */
    public int getY() {
        return y;
    }

    /** Modificadora que assigna una columna a la casella 
     * @param y Columna a assignar a la casella */
    public void setY(int y) {
        this.y = y;
    }

    /** Modificadora que assigna un valor a la casella 
     * @param valor Valor a assignar a la casella */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /** Modificadora que assigna un identificador de zona a la casella 
     * @param iz Identificador de zona a assignar a la casella */
    public void setZonaId(int iz){
        idz = iz;
    }

    /** Consultora que retorna l'identificador de la zona de la casella */
    public int consultaZonaId(){
        return idz;
    }


}