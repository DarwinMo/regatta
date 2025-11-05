package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MediumSailTest {

    @Test
    void testAdjustAngle() {
        MediumSail sail = new MediumSail();

        assertEquals(0, sail.adjustAngle(0), "Angle 0 doit rester 0");
        assertEquals(45, sail.adjustAngle(45), "Angle 45 doit rester 45");
        assertEquals(180, sail.adjustAngle(180), "Angle 180 doit rester 180");
    }

    @Test
    void testAdjustSpeed() {
        MediumSail sail = new MediumSail();

        assertEquals(0, sail.adjustSpeed(0, 0), "Vitesse 0 doit rester 0");
        assertEquals(5.5, sail.adjustSpeed(5.5, 45), "Vitesse 5.5 doit rester 5.5");
        assertEquals(10.0, sail.adjustSpeed(10.0, 180), "Vitesse 10 doit rester 10");
    }
}
