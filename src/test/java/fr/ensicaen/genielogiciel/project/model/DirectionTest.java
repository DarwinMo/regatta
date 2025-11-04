package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    private Direction boat;

    @BeforeEach
    void setUp() {
        boat = new Direction();
    }

    @Test
    void testInitialState() {
        assertEquals(0.0, boat.getAngle(), 0.0001, "L'angle initial doit être 0°");
        assertEquals(0.0, boat.getX(), 0.0001, "La position X initiale doit être 0");
        assertEquals(0.0, boat.getY(), 0.0001, "La position Y initiale doit être 0");
        assertEquals(0.0, boat.getDx(), 0.0001, "Le dx initial doit être 0");
        assertEquals(0.0, boat.getDy(), 0.0001, "Le dy initial doit être 0");
    }

    @Test
    void testRotate() {
        boat.rotate(90);
        assertEquals(90.0, boat.getAngle(), 0.0001, "L'angle après rotation de 90° doit être 90°");

        boat.rotate(300);
        // 90 + 300 = 390 -> normalisé en 30°
        assertEquals(30.0, boat.getAngle(), 0.0001, "L'angle doit être normalisé entre 0 et 360°");

        boat.rotate(-50);
        // 30 - 50 = -20 -> normalisé en 340°
        assertEquals(340.0, boat.getAngle(), 0.0001, "L'angle négatif doit être normalisé en positif");
    }

    @Test
    void testMove() {
        boat.rotate(0); // nord
        boat.changeDirection();
        assertEquals(0.0, boat.getX(), 0.0001, "X doit rester 0 en allant vers le nord");
        assertEquals(-1.0, boat.getY(), 0.0001, "Y doit diminuer de 1 en allant vers le nord");
        assertEquals(0.0, boat.getDx(), 0.0001, "dx doit être sin(0°) = 0");
        assertEquals(-1.0, boat.getDy(), 0.0001, "dy doit être -cos(0°) = -1");

        boat.rotate(90); // est
        boat.changeDirection();
        assertEquals(1.0, boat.getX(), 0.0001, "X doit augmenter de 1 en allant vers l'est");
        assertEquals(-1.0, boat.getY(), 0.0001, "Y ne change pas lorsqu'on se déplace vers l'est");
        assertEquals(1.0, boat.getDx(), 0.0001, "dx doit être sin(90°) = 1");
        assertEquals(0.0, boat.getDy(), 0.0001, "dy doit être -cos(90°) ≈ 0");
    }
}