package fr.ensicaen.genielogiciel.project.model;


public interface IBoat {

    void trim();

    void accelerate();

    void applyWind();

    void rotate( int deltaAngle );

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

    double get_windSpeed();

    void set_crew( IDecoratorEquipment crew );

    void set_sail( IDecoratorSail sail );

    boolean hasCrossedFinishLine();

    void setState( IBoatState upState );

    void setTargetSpeed( double polarSpeed );

    void setInCollision( Boolean inCollision );

    void setDX( double dX );

    void setDY( double dY );

    void setX( double x );

    void setY( double y );

    void setSpeed( double speed );
}
