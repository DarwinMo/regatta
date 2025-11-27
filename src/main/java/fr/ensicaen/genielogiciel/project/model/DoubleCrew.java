package fr.ensicaen.genielogiciel.project.model;


public final class DoubleCrew implements IDecoratorEquipment {

    @Override
    public double adjustAngle( double angleDeg ) {
        if (angleDeg < 90) {
            return Math.max(0, angleDeg - 3);
        }
        return angleDeg;
    }

    @Override
    public double adjustSpeed( double speed, double angleDeg ) {
        if (angleDeg < 90) {
            return speed * 1.10;
        } else {
            return speed * 0.90;
        }
    }

    @Override
    public String toString() {
        return "DoubleCrew(2)";
    }
}
