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

    /**
     * Démarre le chronomètre
     */
    public void start() {
        if (isRunning) {
            throw new IllegalStateException("Le chronomètre est déjà en cours d'exécution.");
        }
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
        this.totalTime = 0;
        this.isPaused = false;
    }

    /**
     * Met en pause le chronomètre
     */
    public void pause() {
        if (!isRunning || isPaused) {
            throw new IllegalStateException("Impossible de mettre en pause : le chronomètre n'est pas en cours.");
        }
        this.pauseTime = System.currentTimeMillis();
        this.totalTime += pauseTime - startTime;
        this.isPaused = true;
    }

    /**
     * Reprend le chronomètre après une pause
     */
    public void resume() {
        if (!isPaused) {
            throw new IllegalStateException("Impossible de reprendre : le chronomètre n'est pas en pause.");
        }
        this.startTime = System.currentTimeMillis();
        this.isPaused = false;
    }

    /**
     * Arrête le chronomètre et calcule le temps total
     */
    public void finish() {
        if (!isRunning) {
            throw new IllegalStateException("Le chronomètre n'est pas en cours d'exécution.");
        }
        this.finishTime = System.currentTimeMillis();
        this.totalTime = this.finishTime - this.startTime;
        this.isRunning = false;
    }

    /**
     * Retourne le temps total en millisecondes
     * 
     * @return le temps total en ms, ou 0 si le chronomètre n'a pas été arrêté
     */
    public long getTotalTime() {
        return totalTime;
    }

    /**
     * Retourne le temps total formaté en minutes:secondes:millisecondes
     * 
     * @return le temps formaté
     */
    public String getFormattedTime() {
        long minutes = (totalTime / 60000) % 60;
        long seconds = (totalTime / 1000) % 60;
        long milliseconds = totalTime % 1000;

        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }

    /**
     * Vérifie si le chronomètre est en cours d'exécution
     * 
     * @return true si le chronomètre est en cours d'exécution
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Réinitialise le chronomètre
     */
    public void reset() {
        this.startTime = 0;
        this.finishTime = 0;
        this.totalTime = 0;
        this.isRunning = false;
    }

    public void resume() {

    }

    public boolean isPaused() {
        return false;
    }

    public void pause() {

    }

}