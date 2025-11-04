package fr.ensicaen.genielogiciel.project.model;

import model.Figaro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FigaroTest {

    private Figaro boat;

    @BeforeEach
    void setUp() {
        boat = new Figaro();
        boat.setWindAngle(0);
        boat.setWindSpeed(15);
        boat.setCrewNumber(1);
        boat.setSailSize(0);
    }

    @Test
    void testInitialPosition() {
        assertEquals(580, boat.getX());
        assertEquals(480, boat.getY());
        assertEquals(0, boat.getAngle());
        assertEquals(0, boat.getSpeed());
    }

    @Test
    void testRotate() {
        boat.rotate(10);
        assertEquals(10, boat.getAngle());
        boat.rotate(-20);
        assertEquals(350, boat.getAngle());
    }

    @Test
    void testTrimAdjustsAngle() {
        boat.setAngle(30);
        boat.setWindAngle(0);
        boat.trim();
        assertTrue(boat.getAngle() < 30, "Trim should rotate toward wind direction");
    }

    @Test
    void testAccelerateIncreasesSpeed() {
        boat.accelerate();
        assertTrue(boat.getSpeed() > 0, "Speed should increase after acceleration");
    }

    @Test
    void testApplyWindChangesPosition() {
        boat.setSpeed(2);
        double oldX = boat.getX();
        double oldY = boat.getY();
        boat.applyWind();
        assertTrue(boat.getX() != oldX || boat.getY() != oldY, "Position should change with wind application");
    }

    @Test
    void testCrewInfluence() {
        boat.setCrewNumber(2);
        boat.accelerate();
        double speedTwoCrew = boat.getSpeed();

        boat.setCrewNumber(1);
        boat.accelerate();
        double speedOneCrew = boat.getSpeed();

        assertNotEquals(speedOneCrew, speedTwoCrew, "Crew number should influence speed");
    }

    @Test
    void testSailSizeInfluence() {
        boat.setSailSize(1);
        boat.accelerate();
        double speedLargeSail = boat.getSpeed();

        boat.setSailSize(0);
        boat.accelerate();
        double speedNormalSail = boat.getSpeed();

        assertNotEquals(speedNormalSail, speedLargeSail, "Sail size should influence speed");
    }
}
