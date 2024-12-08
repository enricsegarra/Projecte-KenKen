package Domini;

/**Classe myException, la qual hereta de la classe Exception. Serveix per a enviar excepcions amb el missatge que es desitgi*/
public class myException extends Exception {
    
    /**Creadora de la classe myException*/
    public myException() {
        super();
    }

    /**Creadora de la classe myException, se li passa un string s com a paràmetre que serà el nou missatge de l'excepció
     * @param s Nou missatge de l'excepció */
    public myException(String s) {
        super(s);
    }
}