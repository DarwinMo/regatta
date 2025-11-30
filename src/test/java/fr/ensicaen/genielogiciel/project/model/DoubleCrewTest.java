package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DoubleCrewTest {

    private static final double EPS = 1e-9;

    @Test
    void adjustAngle_betterUpwind_onlyWhenLessThan90() {
        IDecoratorEquipment c = new DoubleCrew();

        assertTrue(c.adjustAngle(45.0) < 45.0);
        assertEquals(0.0, c.adjustAngle(2.0), EPS);

        assertEquals(90.0, c.adjustAngle(90.0), EPS);

        assertEquals(120.0, c.adjustAngle(120.0), EPS);
    }

    @Test
    void adjustSpeed_improvesUpwind_andReducesDownwind() {
        IDecoratorEquipment c = new DoubleCrew();

        double baseUp = 6.0;
        double up = c.adjustSpeed(baseUp, 45.0);
        assertEquals(baseUp * 1.10, up, EPS);

        double baseDown = 8.0;
        double down1 = c.adjustSpeed(baseDown, 90.0);
        double down2 = c.adjustSpeed(baseDown, 150.0);
        assertEquals(baseDown * 0.90, down1, EPS);
        assertEquals(baseDown * 0.90, down2, EPS);
    }
}