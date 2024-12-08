package Domini;

import java.util.*;

/**Classe Zona*/
public class Zona {
    /**Enter amb el resultat esperat de la zona*/
    private int resultat;
    /**Enter que identifica quina operació te la zona*/
    private int op;
    /**ArrayList amb totes les caselles de la zona*/
    protected ArrayList<Casella> vcas; 
    /**Operacio de la zona (si en té)*/
    protected Operacio operacio;
    
    /** Creadora de Zona buida, s’inicialitza l’array de caselles i es posa op a -1 per determinar que la casella encara no té operació. (El valor 0 per op significa que la casella no té operació, és a dir qualsevol número és vàlid per emplenar-la)*/
    public Zona() {
       resultat = op = -1;
       vcas = new ArrayList<Casella>();
    };

    /** Creadora de Zona que inicialitza el resultat i l’operació d’aquesta. Si op > 0 s’assigna un objecte Operació, contràriament, l'operació serà 0
     * @param resultat Resultat de la zona
     * @param op Identificador de l'operació de la zona (entre 0 i 6) */
    public Zona(int resultat, int op) {
        this.resultat = resultat;
        if(op > 0){
            assignaOperacio(op);
        }
        vcas = new ArrayList<Casella>();
        this.op = op;
    }

    /** Funció que s’encarrega de crear i assignar un objecte Operació a la Zona
     * @param op Identificador de l'operació de la zona (aquí entre 1 i 6) */
    public void assignaOperacio(int op) {
           switch (op) {
                case 1:
                    operacio = new Suma();
                    break;
                case 2:
                    operacio = new Resta();
                    break;
                case 3:
                    operacio = new Multiplicacio();
                    break;
                case 4:
                    operacio = new Divisio();
                    break;
                case 5:
                    operacio = new Potencia();
                    break;
                case 6:
                    operacio = new Modul();
                    break;
            }
    }

    /** Funció que s’encarrega de comprovar si el valor v és vàlid per a la casella amb posició (x,y)
     * @param c Casella a afegir a la zona */
    public void setCasella(Casella c){ 
         vcas.add(c);
    }

    /** Modificadora que assigna el valor v a la casella passada com a paràmetre
     * @param cas Casella a modificar
     * @param v Valor a introduir */
    public void setValor(Casella cas, int v) {
        cas.setValor(v);  
    }

    /**Consultora que retorna el nombre de caselles de la zona */
    public int consultaTamanyCaselles() {
        return vcas.size();
    }

    /**Modificadora que assigna un identificador d'operació a la zona
     * @param op Identificador d'operació a assignar a la zona */
    public void setOperacio(int op) {
        this.op = op;
    }

    /**Consultora que retorna l'identificador de l'operació de la zona */
    public int consultaOperacio() {
        return op;
    }

    /**Modificadora que assigna un resultat a la zona
     * @param v Resultat a assignar a la zona */
    public void setTotal(int v) {
        resultat = v;
    }

    /**Consultora que retorna el resultat de la zona */
    public int consultaTotal() {
        return resultat;
    }

    /**Consultora que retorna la casella identificada per id 
     * @param id Identificador de la casella a la zona */
    public Casella consultaCasella(int id) {
        return vcas.get(id);
    }

    /**Comprova que el valor v sigui vàlid a a la zona per a la casella amb posició (x,y)
     * @param x Fila de la Casella a consultar
     * @param y Columna de la Casella a consultar
     * @param v Valor a consultar */
    public boolean valorValid(int x, int y, int v) {
        if (op == 0) {
            vcas.get(0).setValor(v);
            return true;
        }
        return operacio.posValida(x,y,v, this);
    }
    
    /**Consultora que retorna si la casella cas te la posició (x,y)
     * @param cas Casella a consultar 
     * @param x Fila de la casella (a consultar) 
     * @param y Columna de la casella (a consultar) */
    public boolean esCasella(Casella cas, int x, int y){
        return cas.esCasella(x,y);
    }

    /**Consultora que retorna el valor de la casella cas
     * @param cas Casella a consultar */
    public int getValorCasella(Casella cas){
        return cas.consultaValor();
    }
    
    /**Consultora que retorna el resultat de la zona */
    public int getTotal(){
        return resultat;
    }
    
    /**Consultora que retorna la casella identificada per id 
     * @param id Identificador de la casella a la zona */
    public Casella getCasella(int id){
        return vcas.get(id);
    }
}
