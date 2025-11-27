package fr.ensicaen.genielogiciel.project.model;


public final class SingleCrew implements IDecoratorEquipment {

    @Override
    public double adjustAngle( double angleDeg ) {
        return angleDeg;
    }

    @Override
    public double adjustSpeed( double speed, double angleDeg ) {
        return speed;
    }

    @Override
    public String toString() {
        return "SingleCrew(1)";
    }
}