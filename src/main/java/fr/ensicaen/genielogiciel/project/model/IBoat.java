package fr.ensicaen.genielogiciel.project.model;

/**
 * Represents a generic sailboat in the regatta simulation.
 * Defines the essential actions and state accessors that all boats must implement.
 */
public interface IBoat {

    void trim();

    void accelerate();

    void applyWind();

    void rotate( int deltaAngle );

    void move();

    double get_x();

    double get_y();

    double get_dx();

    double get_dy();

    int get_angle();

    void set_angle( int angle );

    double get_speed();

    void set_speed( double speed );


    void set_windAngle( double windAngle );


    void set_windSpeed( double windSpeed );


    void set_crew( IDecoratorEquipment crew );

    void set_sail( IDecoratorSail sail );
}
