package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FigaroTest {

    private IBoat boat;

    @BeforeEach
    void setUp() {
        boat = new FigaroFactory(new MediumSail(), new SingleCrew()).createFigaro();
        boat.set_windAngle(0);
        boat.set_windSpeed(15);
    }

    @Test
    void testInitialPosition() {
        assertEquals(580, boat.get_x());
        assertEquals(480, boat.get_y());
        assertEquals(0, boat.get_angle());
        assertEquals(0, boat.get_speed());
    }

    @Test
    void testRotate() {
        boat.rotate(10);
        assertEquals(10, boat.get_angle());
        boat.rotate(-20);
        assertEquals(350, boat.get_angle());
    }

    @Test
    void testTrimAdjustsAngle() {
        boat.set_angle(30);
        boat.set_windAngle(0);
        boat.trim();
        assertTrue(boat.get_angle() < 30, "Trim should rotate toward wind direction");
    }

    @Test
    void testAccelerateIncreasesSpeed() {
        boat.accelerate();
        assertTrue(boat.get_speed() > 0, "Speed should increase after acceleration");
    }

    @Test
    void testApplyWindChangesPosition() {
        boat.set_speed(2);
        double oldX = boat.get_x();
        double oldY = boat.get_y();
        boat.applyWind();
        assertTrue(boat.get_x() != oldX || boat.get_y() != oldY, "Position should change with wind application");
    }

    @Test
    void testCrewInfluence() {
        boat.set_crew(new SingleCrew());
        boat.accelerate();
        double speedOneCrew = boat.get_speed();

        boat.set_crew(new DoubleCrew());
        boat.accelerate();
        double speedTwoCrew = boat.get_speed();

        assertNotEquals(speedOneCrew, speedTwoCrew, "Crew number should influence speed");
    }

    @Test
    void testSailSizeInfluence() {
        boat.set_sail(new MediumSail());
        boat.accelerate();
        double speedLargeSail = boat.get_speed();

        boat.set_sail(new LargeSail());
        boat.accelerate();
        double speedNormalSail = boat.get_speed();

        assertNotEquals(speedNormalSail, speedLargeSail, "Sail size should influence speed");
    }
}
