package fr.ensicaen.genielogiciel.project.model;

/**
 * 1 équipier : configuration de base (aucune modification de la polaire).
 */
public final class SingleCrew implements IDecoratorEquipment {

    @Override
    public double adjustAngle( double angleDeg ) {
        return angleDeg; // neutre
    }

    @Override
    public double adjustSpeed( double speed, double angleDeg ) {
        return speed; // neutre
    }

    @Override
    public String toString() {
        return "SingleCrew(1)";
    }
}