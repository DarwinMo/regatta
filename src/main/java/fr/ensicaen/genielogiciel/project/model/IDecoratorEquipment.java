package fr.ensicaen.genielogiciel.project.model;


public interface IDecoratorEquipment {
    double adjustAngle( double angleDeg );

    double adjustSpeed( double speed, double angleDeg );
}