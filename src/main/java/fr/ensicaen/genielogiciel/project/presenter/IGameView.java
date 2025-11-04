package fr.ensicaen.genielogiciel.project.presenter;

public interface IGameView {
    void addBoat( double x, double y );

    void update( double dx, double dy, double angle );
}
