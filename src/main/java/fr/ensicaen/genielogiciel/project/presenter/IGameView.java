package fr.ensicaen.genielogiciel.project.presenter;

public interface IGameView {
    void addBoat( double x, double y );

    void addBuoys( double x, double y );

    void addCoast( double s_x, double s_y, double e_x, double e_y );

    void update( double dx, double dy, double angle );
}
