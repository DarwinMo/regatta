package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class Oceanis37Test {

    private IBoat boat;

    @BeforeEach
    void setUp() throws IOException {
        boat = new Oceanis37Factory(new MediumSail(), new SingleCrew()).createBoat();
        boat.set_windAngle(90);
        boat.set_windSpeed(10);
        boat.set_angle(0);
    }

    @Test
    void testInitialPosition() {
        assertEquals(580, boat.get_x(), 0.001);
        assertEquals(480, boat.get_y(), 0.001);
        assertEquals(0, boat.get_angle());
        assertEquals(0, boat.get_speed(), 0.001);
    }

    @Test
    void testRotate() {
        boat.rotate(30);
        assertEquals(30, boat.get_angle());
        boat.rotate(-50);
        assertEquals(340, boat.get_angle());
    }

    @Test
    void testTrimAdjustsAngle() {
        boat.set_angle(45);
        boat.set_windAngle(90);
        boat.trim();
        assertTrue(boat.get_angle() > 45 && boat.get_angle() <= 47, "Trim should rotate toward wind direction");
    }

    @Test
    void testAccelerateWithInertia() {
        boat.accelerate();
        double speedBefore = boat.get_speed();

        boat.applyWind();
        boat.applyWind();
        boat.applyWind();

        double speedAfter = boat.get_speed();
        assertTrue(speedAfter > speedBefore, "Speed should increase gradually with inertia");
        assertTrue(speedAfter < boat.get_windSpeed(), "Speed should not instantly reach max wind speed due to inertia");
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
        boat.applyWind();
        double speedOneCrew = boat.get_speed();

        boat.set_crew(new DoubleCrew());
        boat.accelerate();
        boat.applyWind();
        double speedTwoCrew = boat.get_speed();

        assertNotEquals(speedOneCrew, speedTwoCrew, "Crew number should influence speed");
    }

    @Test
    void testSailSizeInfluence() {
        boat.set_sail(new MediumSail());
        boat.accelerate();
        boat.applyWind();
        double speedMediumSail = boat.get_speed();

        boat.set_sail(new LargeSail());
        boat.accelerate();
        boat.applyWind();
        double speedLargeSail = boat.get_speed();

        assertNotEquals(speedMediumSail, speedLargeSail, "Sail size should influence speed");
    }

    @Test
    void testInertiaDecayWhenNoWind() throws IOException {
        boat.set_windSpeed(10);
        boat.set_windAngle(90);
        boat.accelerate();

        for (int i = 0; i < 10; i++) {
            boat.applyWind();
        }

        double initialSpeed = boat.get_speed();

        boat.set_windSpeed(0);
        boat.accelerate();

        for (int i = 0; i < 10; i++) {
            boat.applyWind();
        }

        double reducedSpeed = boat.get_speed();

        assertTrue(reducedSpeed < initialSpeed && reducedSpeed > 0, "Speed should decay when wind speed drops due to inertia");
    }

}
