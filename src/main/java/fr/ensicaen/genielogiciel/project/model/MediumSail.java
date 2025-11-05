package fr.ensicaen.genielogiciel.project.model;

public class MediumSail implements IDecoratorSail {

    @Override
    public double adjustAngle(double windAngle){
        return windAngle;
    }

    @Override
    public double adjustSpeed(double baseSpeed, double windAngle) {
        return baseSpeed;
    }
}
