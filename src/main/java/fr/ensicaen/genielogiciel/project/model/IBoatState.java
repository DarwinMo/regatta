package fr.ensicaen.genielogiciel.project.model;

public interface IBoatState {
    void start( IBoat boat );

    void stop( IBoat boat );

    boolean isRunning();
}
