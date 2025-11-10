package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CrewModifierContractTest {

    private static final double EPS = 1e-9;

    @Test
    void singleCrew_preservesBounds() {
        IDecoratorEquipment c = new SingleCrew();
        assertTrue(c.adjustAngle(0.0) >= 0.0 - EPS);
        assertTrue(c.adjustAngle(180.0) <= 180.0 + EPS);
    }

    @Test
    void doubleCrew_preservesBounds() {
        IDecoratorEquipment c = new DoubleCrew();
        assertTrue(c.adjustAngle(0.0) >= 0.0 - EPS);
        assertTrue(c.adjustAngle(180.0) <= 180.0 + EPS);
    }
}