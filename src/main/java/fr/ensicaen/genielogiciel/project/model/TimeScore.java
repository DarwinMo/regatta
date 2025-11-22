package fr.ensicaen.genielogiciel.project.model;

/**
 * Classe pour mesurer le temps écoulé entre un démarrage et un arrêt
 */
public class TimeScore {
    private long startTime;
    private long finishTime;
    private long totalTime;
    private long pauseTime;
    private boolean isRunning;
    private boolean isPaused;

    public void start() {
        if (isRunning) {
            throw new IllegalStateException("Le chronomètre est déjà en cours d'exécution.");
        }
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
        this.totalTime = 0;
        this.isPaused = false;
    }

    public void pause() {
        if (!isRunning || isPaused) {
            throw new IllegalStateException("Impossible de mettre en pause : le chronomètre n'est pas en cours.");
        }
        this.pauseTime = System.currentTimeMillis();
        this.totalTime += pauseTime - startTime;
        this.isPaused = true;
    }

    public void resume() {
        if (!isPaused) {
            throw new IllegalStateException("Impossible de reprendre : le chronomètre n'est pas en pause.");
        }
        this.startTime = System.currentTimeMillis();
        this.isPaused = false;
    }

    public void finish() {
        if (!isRunning) {
            throw new IllegalStateException("Le chronomètre n'est pas en cours d'exécution.");
        }
        this.finishTime = System.currentTimeMillis();
        this.totalTime = this.finishTime - this.startTime;
        this.isRunning = false;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public String getFormattedTime() {
        long minutes = (totalTime / 60000) % 60;
        long seconds = (totalTime / 1000) % 60;
        long milliseconds = totalTime % 1000;

        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }


    public boolean isRunning() {
        return isRunning;
    }

    public void reset() {
        this.startTime = 0;
        this.finishTime = 0;
        this.totalTime = 0;
        this.isRunning = false;
    }


    public long getCurrentTime() {
        if (!isRunning) {
            return totalTime;
        }
        return totalTime + (System.currentTimeMillis() - startTime);
    }

    public String getFormattedCurrentTime() {
        long t = getCurrentTime();
        long minutes = (t / 60000) % 60;
        long seconds = (t / 1000) % 60;
        long milliseconds = t % 1000;

        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);

    }
}