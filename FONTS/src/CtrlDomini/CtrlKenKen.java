package CtrlDomini;

import Domini.*;

import java.util.*;

/**Classe per al controlador de kenkens*/
public class CtrlKenKen {
    /**Matriu amb les direccions que pot utilitzar l'algorisme de generació de kenkens*/
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /** Creadora del Controlador*/
    public CtrlKenKen() {}

    /** Aquesta funció s’ocupa de crear un KenKen a partir d'una matriu d'enters que se li passa com a paràmetre
     * @param cap ArrayList d'ArrayLists d'enters a partir del que es crea un KenKen */
    public KenKen Crea_kenken(ArrayList<ArrayList<Integer> > cap) throws Exception{
        int tam = cap.get(0).get(0);
        int nreg = cap.get(0).get(1);
        KenKen K = new KenKen(tam);
        for (int i = 1; i < nreg+1; ++i) {
            int op = cap.get(i).get(0);
            int res = cap.get(i).get(1);
            int ncas = cap.get(i).get(2);
            Zona z = new Zona(res,op);
            int extra = 0;
            for (int j = 0; j < ncas*2; j += 2) {
                int it = 3+j+extra;
                int x = cap.get(i).get(it);
                int y = cap.get(i).get(it+1);
                Casella cas = new Casella(i-1,x-1,y-1);
                if (it+2 < cap.get(i).size() && cap.get(i).get(it+2) > 10 && cap.get(i).get(it+2) <= 10 + tam) {
                    int v = cap.get(i).get(it+2) - 10;
                    extra += 1;
                    if (K.PosicioValidaTauler(x-1, y-1, v)) {
                        cas.setValor(v);
                        cas.setPre();
                    }
                    else {
                        myException me = new myException("El valor " + v + " no es valid per a la casella amb x = " + x + " i y = " + y);
                        throw me;
                    }
                }
                K.setCasella(cas,x-1,y-1);
                z.setCasella(cas);
            }
            K.setZona(z);
        }
        return K;
    }

    /** Funció de backtracking que comprova si el KenKen passat per paràmetre es pot resoldre i, en cas afirmatiu, hi guarda una solució
     * @param K KenKen a validar
     * @param nx Fila de la casella actual
     * @param ny Columna de la casella actual */
    public void validaKenKen(KenKen K, int nx, int ny) throws Exception { 
        int tam = K.consultaTamany();
        if (nx < tam) { 
            if (K.consultaValorCasella(nx, ny) == 0) {
                int i = 1;
                while (i <= tam && !K.consultaVerified()) {
                    boolean p = K.PosicioValida(nx,ny,i);
                    if (p && ny == tam-1) {
                        validaKenKen(K,nx+1,0);
                    }
                    else if (p) {
                        validaKenKen(K,nx,ny+1);
                    }
                    ++i;
                    }
                if (!K.consultaVerified()) {       
                    K.setValor(nx,ny,0);
                }
            }
            else {
                if (ny == tam-1) validaKenKen(K,nx+1,0);
                else validaKenKen(K,nx,ny+1);
            }
        }
        else K.setVerified();
    }

    /** Retorna un possible valor per a la casella amb posició (x,y), tal que sigui vàlid actualment i a futur per a la zona. En cas de no haver-hi retorna un -1
     * @param K KenKen on introduir la pista
     * @param x Fila de la casella
     * @param y Columna de la casella */
    public int consultaPista(KenKen K, int x, int y) {
        int tam = K.consultaTamany();              
        for (int i = 1; i <= tam; ++i) {
            if (K.PosicioValidaPista(x,y,i)) return i;
        }
        return -1;
    }

    /** Aquesta funció genera un KenKen de forma aleatòria a partir del tamany indicat per paràmetre
     * @param size Mida del KenKen a generar aleatòriament */
    public KenKen generaAleatori(int size) {
        KenKen tauler = new KenKen(size);
        Set<Integer>[] rowSet = new HashSet[size];
        Set<Integer>[] colSet = new HashSet[size];
        for (int i = 0; i < size; i++) {
            rowSet[i] = new HashSet<>();
            colSet[i] = new HashSet<>();
        }
        generaTaulAle(tauler, size, 0, 0, rowSet, colSet);
        boolean valid = false;                                          //Es donaven casos on l'algoritme de backtracking fallava i s'emplenava el tauler de 0s, per assegurar que no passa comprovem la primera posició després de la generació, i en cas de ser 0 tornem a generar fins a tenir un correcte.
        while (!valid) {
            if (tauler.consultaValorCasella(0,0) != 0) valid = true;
            if (!valid) {
                for (int i = 0; i < size; i++) {
                    rowSet[i] = new HashSet<>();
                    colSet[i] = new HashSet<>();
                }
                generaTaulAle(tauler, size, 0, 0, rowSet, colSet);
            }
        }
        creaZones(tauler);
        return tauler;
    }

    /** Funció d’ús per a generaAleatori(int size). Aquesta funció s’encarrega d’emplenar el tauler amb valors aleatoris de forma correcta per la normativa d’un KenKen
     * @param tauler KenKen a plenar
     * @param size Mida del KenKen a plenar
     * @param row Iterador de la fila actual
     * @param col Iterador de la columna actual
     * @param rowSet Set de valors fets servir a cada fila
     * @param colSet Set de valors fets servir a cada columna*/
    private boolean generaTaulAle(KenKen tauler, int size, int row, int col, Set<Integer>[] rowSet, Set<Integer>[] colSet) {
        if (row == size) {
            return true;    //Cas Base
        }

        Random random = new Random();
        for (int i = 1; i <= size; i++) {
            int num = random.nextInt(size) + 1; //Numero aleatori entre 1 i tamany
            if (!rowSet[row].contains(num) && !colSet[col].contains(num)) {
                tauler.setValor(row,col,num);
                rowSet[row].add(num);
                colSet[col].add(num);
                
                int nextRow = row + (col + 1) / size;   //Següent fila si és necessari
                int nextCol = (col + 1) % size;         //Següent columna
                
                if (generaTaulAle(tauler, size, nextRow, nextCol, rowSet, colSet)) {
                    return true;
                }
                //Backtracking:
                rowSet[row].remove(num);
                colSet[col].remove(num);
                tauler.setValor(row,col,0);
            }
        }
        return false;
    }

    /** Algorisme encarregat de generar les zones del KenKen. Com indica el seu nom fa servir un mètode BFS per assignar zones recorrent el tauler de forma iterativa
     * @param tauler KenKen al que assignar Zones
     * @param z Zona actual a definir
     * @param startX Coordenada de la fila desde la que es crida la funció
     * @param startY Coordenada de la columna desde la que es crida la funció
     * @param rand Generador aleatori que es reutilitza */
    private void bfsZones(KenKen tauler, Zona z, int startX, int startY, Random rand) {
        int rows = tauler.consultaTamany();
        int cols = tauler.consultaTamany();

        Queue<Casella> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        int maxPartitionSize = 4;
        
        queue.add(tauler.consultaCasella(startX,startY));       //Afegim casella a la cua de BFS
        visited.add(startX * cols + startY);                    //Marquem casella com a visitada

        tauler.setCasellaZonaId(startX,startY,tauler.consultaTamanyZones());      //Posem el id de zona de la casella a modificar
        z.setCasella(tauler.consultaCasella(startX,startY));                        //Afegim la casella a la zona actual

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(tauler.consultaValorCasella(startX, startY));                       //Afegim el número a l'array de valors
        maxPartitionSize--;     //Reduim el nombre maxim de caselles que pot tenir la zona

        int surt = -1;

        while (!queue.isEmpty() && maxPartitionSize > 0 && surt != 1) {
            Casella current = queue.poll();
            int x = current.getX();
            int y = current.getY();

            List<Integer> directionsList = Arrays.asList(0, 1, 2, 3);
            Collections.shuffle(directionsList, rand);

            for (int dirIndex : directionsList) {
                int[] dir = directions[dirIndex];
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && !visited.contains(newX * cols + newY) && tauler.consultaCasellaZonaId(newX,newY) == -1) {        //Dins el tauler i casella no visitada
                    queue.add(tauler.consultaCasella(newX,newY));
                    visited.add(newX * cols + newY);
                    tauler.setCasellaZonaId(newX,newY,tauler.consultaTamanyZones());
                    z.setCasella(tauler.consultaCasella(newX,newY));
                    numbers.add(tauler.consultaValorCasella(newX, newY));       //Afegim valor de la casella a l'array de valors
                    maxPartitionSize--;

                    surt = rand.nextInt(2)+1;
                    if (maxPartitionSize < 4 ) {
                        surt = rand.nextInt(1)+1;
                        if (surt != 1) surt = rand.nextInt(1)+1;
                    }
                    if (maxPartitionSize == 0) break;
                }
            }
        }

        int op;
        int index;

        if (numbers.size() == 1) {
            z.assignaOperacio(0);
            z.setOperacio(0);
            z.setTotal(numbers.get(0));         //Si només hi ha una casella, el resultat és el valor d'aquesta
        }
        else if (numbers.size() == 2) {
            int dosCaselles[] = {2,4,5,6};
            int dosCasellesNoPot[] = {2,4,6};

            index = rand.nextInt(4);
            op = dosCaselles[index];
            z.assignaOperacio(op);
            z.setOperacio(op);
            int max = Collections.max(numbers);
            int min = Collections.min(numbers);
            int res = 0;
            if (op == 2) {
                res = max - min;
            }
            else if (op == 4) {
                res = max / min;
            }
            else if (op == 6) {
                res = max % min;
            }
            else if (op == 5){
                int select = rand.nextInt(2);
                int base = (select == 0) ? min : max;
                int exp = (select == 0) ? max : min;
                res = 1;
                for (int k = 0; k < exp; ++k) res *= base;

                if (res > 99999) {
                    index = rand.nextInt(3);
                    op = dosCaselles[index];

                    res = 0;
                    if (op == 2) {
                        res = max - min;
                    }
                    else if (op == 4) {
                        res = max / min;
                    }
                    else if (op == 6) {
                        res = max % min;
                    }
                } 
            }
            z.setTotal(res);
            numbers = new ArrayList<>();
        }
        else if (numbers.size() > 2) {
            int mesDeDos[] = {1,3};

            index = rand.nextInt(2);
            op = mesDeDos[index];
            z.assignaOperacio(op);
            z.setOperacio(op);

            if (op == 1) {
                int res = 0;
                for (int n : numbers) {
                    res += n;
                }
                z.setTotal(res);
            }
            else if (op == 3) {
                int res = 1;
                for (int n : numbers) {
                    res *= n;
                }
                z.setTotal(res);
            }
        }
        tauler.setZona(z);      //Afegim la zona creada amb les caselles escollides al tauler
    }

    /** Aquesta funció prepara una sèrie d’arrays per tal que bfsZones pugui funcionar correctament
     * @param tauler KenKen al que se li assignaran les Zones generades*/
    private void creaZones(KenKen tauler) {
        int rows = tauler.consultaTamany();
        int cols = tauler.consultaTamany();
        Random rand = new Random();
        Zona z = new Zona();

        List<Integer> casNoAssig = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                casNoAssig.add(i * cols + j);
            }
        }
        Collections.shuffle(casNoAssig, rand);

        for (int cas : casNoAssig) {
            int i = cas / cols;
            int j = cas % cols;
            if (tauler.consultaCasellaZonaId(i,j) == -1) { // If cell unassigned
                bfsZones(tauler, z, i, j, rand);
                z = new Zona();
            }
        }
    }
}