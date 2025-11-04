package model;

/**
 * Represents a generic sailboat in the regatta simulation.
 * Defines the essential actions and state accessors that all boats must implement.
 */
public interface IBoat {


    void trim();

    void accelerate();


    void applyWind();


    void rotate(int deltaAngle);


    void move();

    double getX();
    void setX(double x);

    double getY();
    void setY(double y);

    int getAngle();
    void setAngle(int angle);

    double getSpeed();
    void setSpeed(double speed);


    void setWindAngle(double windAngle);


    void setWindSpeed(double windSpeed);


    void setCrewNumber(int crewNumber);

    void setSailSize(int sailSize);
}
