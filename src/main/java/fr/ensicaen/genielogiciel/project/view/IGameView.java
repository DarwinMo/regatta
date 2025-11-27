package fr.ensicaen.genielogiciel.project.view;

import fr.ensicaen.genielogiciel.project.model.Line;

public interface IGameView {
    void addBoat( double x, double y );

    void updateBoat( double dx, double dy, double angle );

    void updateDashboard( double windDir, double boatDir, double speed, String time );

    void addBuoys( double x, double y );


    void addBuoyLines( double s_x, double s_y, double e_x, double e_y );

    void addCoast( double start_x, double start_y, double end_x, double end_y );

    void addEndScreen( String time );
}
