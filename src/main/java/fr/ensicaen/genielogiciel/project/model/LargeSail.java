package fr.ensicaen.genielogiciel.project.model;

public final class LargeSail implements IDecoratorSail {

    @Override
    public double adjustAngle( double angleDeg ) {
        if (angleDeg < 90) {
            return Math.min(180, angleDeg + 3);
        }
        return angleDeg;
    }

    @Override
    public double adjustSpeed( double speed, double angleDeg ) {
        if (angleDeg < 90) {
            return speed * 0.9;
        } else {
            return speed * 1.1;
        }
    }
}
