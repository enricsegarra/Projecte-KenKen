package Domini;

/**Classe Timer*/
public class Timer {
    /**Enter (long) amb el temps d'inici del Timer*/
    private long startTime;
    /**Enter (long) amb el temps del Timer quan s'ha pausat*/
    private long pausedTime;
    /**Booleà que identifica si el Timer està en ús o no*/
    private boolean isRunning;

    /** Creadora del Timer. S’inicialitza running com a fals*/
    public Timer() {
        isRunning = false;
    }

    /** Creadora del Timer. S'inicialitza running com a false, i el temps inicial és el passat per paràmetre
     * @param lastTimeSaved Indica el temps en el que s'ha d'iniciar el timer */
    public Timer(int lastTimeSaved) {
        isRunning = false;
        pausedTime = lastTimeSaved*1000L;
    }

    /** Inicialitza el Timer si running == false*/
    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    /** Pausa el Timer si running == true*/
    public void pause() {
        if (isRunning) {
            pausedTime = System.currentTimeMillis() - startTime;
            isRunning = false;
        }
    }

    /** Continua el Timer a partir del moment pausat si running == false*/
    public void resume() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - pausedTime;
            isRunning = true;
        }
    }

    /**  Retorna el temps transcurrit des de l’inici del Timer*/
    public int getTimeElapsed() {
        long elapsedTime;
        if (isRunning) {
            elapsedTime = System.currentTimeMillis() - startTime;
        } else {
            elapsedTime = pausedTime;
        }
        int seconds = (int)(elapsedTime / 1000);

        return seconds;
    }
}


