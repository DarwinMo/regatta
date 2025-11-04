package fr.ensicaen.genielogiciel.project.model;

import model.CrewModifier;
import model.SingleCrew;
import model.DoubleCrew;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CrewModifierContractTest {

    private static final double EPS = 1e-9;

    @Test
    void singleCrew_preservesBounds() {
        CrewModifier c = new SingleCrew();
        assertTrue(c.adjustAngle(0.0)   >= 0.0 - EPS);
        assertTrue(c.adjustAngle(180.0) <= 180.0 + EPS);
    }

    @Test
    void doubleCrew_preservesBounds() {
        CrewModifier c = new DoubleCrew();
        assertTrue(c.adjustAngle(0.0)   >= 0.0 - EPS);
        assertTrue(c.adjustAngle(180.0) <= 180.0 + EPS);
    }
}