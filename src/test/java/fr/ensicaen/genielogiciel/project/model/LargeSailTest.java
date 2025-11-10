package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LargeSailTest {

    @Test
    void testAdjustAngle() {
        LargeSail sail = new LargeSail();

        assertEquals(3, sail.adjustAngle(0), "Angle 0 doit devenir 3");
        assertEquals(45 + 3, sail.adjustAngle(45), "Angle 45 doit devenir 48");
        assertEquals(89 + 3, sail.adjustAngle(89), "Angle 89 doit devenir 92");

        assertEquals(90, sail.adjustAngle(90), "Angle 90 reste 90");
        assertEquals(120, sail.adjustAngle(120), "Angle 120 reste 120");
        assertEquals(180, sail.adjustAngle(180), "Angle 180 reste 180");
    }

    @Test
    void testAdjustSpeed() {
        LargeSail sail = new LargeSail();

        assertEquals(0.9 * 10, sail.adjustSpeed(10, 0), 1e-6, "Vitesse 10 à angle 0 doit devenir 9");
        assertEquals(0.9 * 5.5, sail.adjustSpeed(5.5, 45), 1e-6, "Vitesse 5.5 à angle 45 doit devenir 4.95");

        assertEquals(11.0, sail.adjustSpeed(10, 90), 1e-6, "Vitesse 10 à angle 90 doit devenir 11");
        assertEquals(16.5, sail.adjustSpeed(15, 120), 1e-6, "Vitesse 15 à angle 120 doit devenir 16.5");
    }
}
