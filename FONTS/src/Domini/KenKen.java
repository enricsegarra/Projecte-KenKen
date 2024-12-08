package Domini;

import java.util.*;

/**Classe KenKen*/
public class KenKen {
    /**Enter amb la mida del kenken*/
    private int tamany;
    /**Matriu de les caselles del kenken*/
    private ArrayList<ArrayList<Casella>> vcas;
    /**ArrayList amb les zones del kenken*/
    private ArrayList<Zona> vzon;
    /**Booleà que identifica si el kenken ha estat verificat (true) o no (false)*/
    private boolean verified;

    /** Crea un KenKen de tamany -1, inicialitza les arraylists buides i posa verified a false*/
    public KenKen () {
        this.tamany = -1;
        this.vcas = new ArrayList<>();
        this.vzon = new ArrayList<>();
        verified = false;
    }
    
    /** Crea un KenKen de la mida indicada pel paràmetre, inicialitza també les arraylists amb caselles i posa verified a false
     * @param tam Mida del KenKen a crear */
    public KenKen (int tam) {
        this.tamany = tam;
        verified = false;
        this.vzon = new ArrayList<>();
        this.vcas = new ArrayList<>();
        for (int i = 0; i < tam; i++) {
            ArrayList<Casella> aux2 = new ArrayList<>();
            for (int j = 0; j < tam; ++j) {
                Casella aux = new Casella(-1,i,j); //El -1 vol dir que no pertany a cap zona actualment
                aux2.add(aux);
            }
            this.vcas.add(aux2);
        }
    }

    /** Serveix per crear una còpia del KenKen passat per paràmetre. El booleà ens indica si hem de copiar tots els valors o només els valors predefinits del KenKen
     * @param k KenKen a copiar
     * @param p Booleà que indica si fer una còpia total o no */
    public KenKen (KenKen k, boolean p) {
        this.tamany = k.tamany;
        this.verified = k.verified;
        this.vcas = new ArrayList<>();
        int zsz = k.vzon.size();
        this.vzon = new ArrayList<>(zsz);
        for (int l = 0; l < zsz; ++l) {
            int op = k.vzon.get(l).consultaOperacio();
            int res = k.vzon.get(l).consultaTotal();
            Zona z = new Zona(res,op);
            this.vzon.add(z);
        }
        for (int i = 0; i < this.tamany; ++i) {
            ArrayList<Casella> aux = new ArrayList<>();
            for (int j = 0; j < this.tamany; ++j) {
                int zid = k.vcas.get(i).get(j).consultaZonaId();
                Casella cas = new Casella(zid,i,j);
                if (k.vcas.get(i).get(j).getPre()) {
                    cas.setValor(k.vcas.get(i).get(j).consultaValor());
                    cas.setPre();
                } 
                else if (p) cas.setValor(k.vcas.get(i).get(j).consultaValor());
                aux.add(cas);
                this.vzon.get(zid).setCasella(cas);
            }
            this.vcas.add(aux);
        }
    }

    /**  Comprova si el valor de la posició indicada no es repeteix en files ni columnes
     * @param x Fila de la casella
     * @param y Columna de la casella 
     * @param v Valor a comprovar */
    public boolean PosicioValidaTauler(int x, int y, int v) {
        for (int i = 0; i < tamany; ++i) {
            if (vcas.get(i).get(y).consultaValor() == v || vcas.get(x).get(i).consultaValor() == v) return false;
        }
        return true;
    }

    /** Comprova si el valor de la posició indicada és vàlid pel tauler i per les zones
     * @param x Fila de la casella
     * @param y Columna de la casella 
     * @param v Valor a comprovar */
    public boolean PosicioValida(int x, int y, int v) { 
        if (!PosicioValidaTauler(x,y,v)) return false;
        int zid = vcas.get(x).get(y).consultaZonaId();
        if(vzon.get(zid).valorValid(x,y,v)) return true;
        return false;
    }

    /** Comprova si la zona té una possible solució
     * @param zid Identificador de la zona a comprovar
     * @param it Iterador per al ArrayList de caselles de zona
     * @param q Array de booleans amb un únic booleà a false, que es posa a true si hi ha una possible solució per a la zona */
    private void validaZona(int zid, int it, boolean[] q) {
        if (it < vzon.get(zid).consultaTamanyCaselles()) {
            Casella cas = vzon.get(zid).getCasella(it);
            int vaux = cas.consultaValor();
            if (vaux == 0) {
                int xaux = cas.getX();
                int yaux = cas.getY();
                int j = 1;
                while (j <= tamany && !q[0]) {
                    if(PosicioValida(xaux,yaux,j)) validaZona(zid,it+1,q);
                    ++j;
                }
                if (!q[0]) cas.setValor(0);
            }
            validaZona(zid,it+1,q);
        }
        q[0] = true;
    }

    /** Comprova si el valor v per a la casella amb posició (x,y) és vàlid actualment per al kenken i si amb aquest valor la zona tindrà una solució a futur
     * @param x Fila de la casella
     * @param y Columna de la casella 
     * @param v Valor a comprovar */
    public boolean PosicioValidaPista(int x, int y, int v) { 
        if (!PosicioValida(x,y,v)) return false;
        int zid = vcas.get(x).get(y).consultaZonaId();
        boolean q[] = {false};
        Zona z = consultaZona(zid);
        ArrayList<Integer> vi = new ArrayList<>();
        ArrayList<Integer> vi2 = new ArrayList<>();
        for (int i = 0; i < z.consultaTamanyCaselles(); ++i){
            Casella cas = z.consultaCasella(i);
            if (cas.consultaValor() > 0) {
                vi.add(i);
                vi2.add(cas.consultaValor());
            }
        }
        validaZona(zid,0,q);
        if (q[0]) {
            for (int k = 0; k < vzon.get(zid).consultaTamanyCaselles(); ++k) {
                Casella cas = vzon.get(zid).getCasella(k);
                if (cas.getX() != x || cas.getY() != y) cas.setValor(0);
            }
            for (int i = 0; i < vi.size(); ++i) {
                z.consultaCasella(vi.get(i)).setValor(vi2.get(i));
            }
            return true;
        }
        else {
            vcas.get(x).get(y).setValor(0);
            for (int i = 0; i < vi.size(); ++i) {
                z.consultaCasella(vi.get(i)).setValor(vi2.get(i));
            }
            return false;
        }
    }
    
    /** Consultora que retorna la mida del KenKen */
    public int consultaTamany() {
        return tamany;
    }

    /** Consultora que retorna si el KenKen està verificat o no */
    public boolean consultaVerified() {
        return verified;
    }

    /** Consultora que retorna la casella amb posició (x,y) 
     * @param x Fila de la casella a consultar
     * @param y Columna de la casella a consultar */
    public Casella consultaCasella(int x, int y) { 
        return vcas.get(x).get(y);
    }

    /** Consultora que retorna la zona amb identificador id 
     * @param id Identificador de la zona a consultar */
    public Zona consultaZona(int id) {
        return vzon.get(id);
    }

    /** Consultora que retorna el valor de la casella amb posició (x,y) 
     * @param x Fila de la casella a consultar
     * @param y Columna de la casella a consultar */
    public int consultaValorCasella(int x, int y) {
        return vcas.get(x).get(y).consultaValor();
    }

    /** Modificador que assigna un identificador de zona a la casella amb posició (x,y)
     * @param x Fila de la casella a modificar
     * @param y Columna de la casella a modificar
     * @param idz Identificador de zona a assignar */
    public void setCasellaZonaId(int x, int y, int idz) {
        vcas.get(x).get(y).setZonaId(idz);
    }

    /** Consultora que retorna l'identificador de la zona de la casella amb posició (x,y) 
     * @param x Fila de la casella a consultar
     * @param y Columna de la casella a consultar */
    public int consultaCasellaZonaId(int x, int y) {
        return vcas.get(x).get(y).consultaZonaId();
    }

    /** Consultora que retorna el nombre de zones del KenKen */
    public int consultaTamanyZones() {
        return vzon.size();
    }

    /** Modificadora que canvia si el KenKen està verificat o no */
    public void setVerified() {
        if (verified) verified = false;
        else verified = true;
    }

    /** Modificadora que assigna la casella cas a la posició (x,y) 
     * @param cas Casella a assignar
     * @param x Fila de la casella a assignar
     * @param y Columna de la casella a assignar */
    public void setCasella(Casella cas, int x, int y) {
        vcas.get(x).set(y,cas);
    }

    /** Modificadora que assigna un valor a la casella amb posició (x,y) 
     * @param x Fila de la casella a modificar
     * @param y Columna de la casella a modificar 
     * @param v Valor a assignar */
    public void setValor(int x, int y, int v) {
        vcas.get(x).get(y).setValor(v);
    }

    /** Modificadora que afegeix una zona al ArrayList de zones del KenKen
     * @param z Zona a assignar */
    public void setZona(Zona z) {
        vzon.add(z);
    }

}