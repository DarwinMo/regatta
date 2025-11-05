package fr.ensicaen.genielogiciel.project.presenter;

public interface Ipresenter {
    public void attach(IGameView gameView);
    public void handleUserEvent(UserAction action);
}
