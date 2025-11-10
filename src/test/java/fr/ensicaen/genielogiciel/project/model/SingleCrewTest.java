package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SingleCrewTest {

    private static final double EPS = 1e-9;

    @Test
    void adjustAngle_isNeutral() {
        IDecoratorEquipment c = new SingleCrew();
        assertEquals(0.0, c.adjustAngle(0.0), EPS);
        assertEquals(30.0, c.adjustAngle(30.0), EPS);
        assertEquals(90.0, c.adjustAngle(90.0), EPS);
        assertEquals(150.0, c.adjustAngle(150.0), EPS);
        assertEquals(180.0, c.adjustAngle(180.0), EPS);
    }

    @Test
    void adjustSpeed_isNeutral() {
        IDecoratorEquipment c = new SingleCrew();
        assertEquals(5.0, c.adjustSpeed(5.0, 45.0), EPS);
        assertEquals(7.2, c.adjustSpeed(7.2, 120.0), EPS);
        assertEquals(0.0, c.adjustSpeed(0.0, 60.0), EPS);
    }
}