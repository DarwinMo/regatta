package fr.ensicaen.genielogiciel.project.model;

public interface IDecoratorSail {

    double adjustAngle(double windAngle);
    double adjustSpeed(double baseSpeed, double windAngle);
}
