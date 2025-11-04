package model;

/**
 * 2 équipiers : meilleur rendement au près, moins bon au portant.
 */
public final class DoubleCrew implements CrewModifier {

    @Override
    public double adjustAngle(double angleDeg) {
        // Optionnel : on peut réduire un peu l’angle pour simuler une meilleure remontée au vent
        if (angleDeg < 90) {
            return Math.max(0, angleDeg - 3); // remonte un peu plus au vent
        }
        return angleDeg;
    }

    @Override
    public double adjustSpeed(double speed, double angleDeg) {
        if (angleDeg < 90) {
            return speed * 1.10; // +10% au près
        } else {
            return speed * 0.90; // -10% au portant
        }
    }

    @Override
    public String toString() {
        return "DoubleCrew(2)";
    }
}
