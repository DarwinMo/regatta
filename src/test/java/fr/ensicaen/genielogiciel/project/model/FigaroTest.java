package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FigaroTest {

    private IBoat boat;

    @BeforeEach
    void setUp() throws IOException {
        boat = new FigaroFactory(new MediumSail(), new SingleCrew()).createFigaro();
        boat.set_windAngle(90);
        boat.set_windSpeed(10);
        boat.set_angle(0);
    }

    @Test
    void testInitialPosition() {
        assertEquals(580, boat.get_x(), 0.001);
        assertEquals(480, boat.get_y(), 0.001);
        assertEquals(0, boat.get_angle());
        assertEquals(0, boat.get_speed());
    }

    @Test
    void testRotate() {
        boat.rotate(15);
        assertEquals(15, boat.get_angle());
        boat.rotate(-30);
        assertEquals(345, boat.get_angle()); // modulo 360
    }

    @Test
    void testTrimAdjustsAngle() {
        boat.set_angle(45);
        boat.set_windAngle(90);
        boat.trim();
        assertTrue(boat.get_angle() > 45 && boat.get_angle() <= 47, "Trim should rotate toward wind direction");
    }

    @Test
    void testAccelerateIncreasesSpeed() {
        boat.accelerate();
        assertTrue(boat.get_speed() > 0, "Speed should increase after acceleration");
    }

    @Test
    void testApplyWindChangesPosition() {
        boat.set_speed(3);
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

        assertTrue(speedTwoCrew != speedOneCrew, "Crew number should influence speed");
    }

    @Test
    void testSailSizeInfluence() {
        boat.set_sail(new MediumSail());
        boat.accelerate();
        double speedMediumSail = boat.get_speed();

        boat.set_sail(new LargeSail());
        boat.accelerate();
        double speedLargeSail = boat.get_speed();

        assertTrue(speedMediumSail != speedLargeSail, "Sail size should influence speed");
    }
}
