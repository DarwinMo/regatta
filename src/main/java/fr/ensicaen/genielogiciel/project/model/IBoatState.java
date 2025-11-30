package fr.ensicaen.genielogiciel.project.model;

public interface IBoatState {
    void accelerate( IDecoratorEquipment crew, IDecoratorSail sail, double adjustedAngle, double polarSpeed, boolean inCollision );

    void applyWind( double targetSpeed, double speed, double angle, double factor );
}
