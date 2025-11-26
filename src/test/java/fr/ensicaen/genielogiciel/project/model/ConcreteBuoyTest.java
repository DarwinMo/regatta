package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteBuoyTest {

    private ConcreteBuoy buoy;
    private IBoat boat;

    @BeforeEach
    void setUp() {
        buoy = new ConcreteBuoy(100, 100, 90);

        boat = new IBoat() {
            private double x = 0;
            private double y = 0;

            @Override
            public double get_x() {
                return x;
            }

            @Override
            public double get_y() {
                return y;
            }

            @Override
            public double get_dx() {
                return 0;
            }

            @Override
            public double get_dy() {
                return 0;
            }

            @Override
            public int get_angle() {
                return 0;
            }

            @Override
            public void set_angle( int angle ) {
            }

            @Override
            public double get_speed() {
                return 0;
            }

            @Override
            public void set_speed( double speed ) {
            }

            @Override
            public void set_windAngle( double windAngle ) {
            }

            @Override
            public void set_windSpeed( double windSpeed ) {
            }

            @Override
            public double get_windSpeed() {
                return 0;
            }

            @Override
            public void set_crew( IDecoratorEquipment crew ) {
            }

            @Override
            public void set_sail( IDecoratorSail sail ) {
            }

            @Override
            public boolean hasCrossedFinishLine() {
                return false;
            }

            @Override
            public void trim() {
            }

            @Override
            public void accelerate() {
            }

            @Override
            public void applyWind() {
            }

            @Override
            public void rotate( int deltaAngle ) {
            }
        };
    }

    @Test
    void testPosition() {
        assertEquals(100, buoy.get_x(), 0.001);
        assertEquals(100, buoy.get_y(), 0.001);
    }

    @Test
    void testPassageValidation() {
        assertFalse(buoy.checkPassage(boat));

        setBoatPosition(100, 100);
        assertTrue(buoy.checkPassage(boat), "Bateau devrait valider l'entrée");

        setBoatPosition(102, 100);
        assertTrue(buoy.checkPassage(boat), "Bateau devrait valider la sortie");

        setBoatPosition(50, 50);
        assertFalse(buoy.checkPassage(boat));
    }

    @Test
    void testCollision() {
        setBoatPosition(0, 0);
        assertFalse(buoy.checkCollision(boat));

        setBoatPosition(105, 100);
        assertTrue(buoy.checkCollision(boat));
    }

    private void setBoatPosition( double x, double y ) {
        ((IBoat) boat).getClass();
        try {
            java.lang.reflect.Field fx = boat.getClass().getDeclaredField("x");
            fx.setAccessible(true);
            fx.setDouble(boat, x);

            java.lang.reflect.Field fy = boat.getClass().getDeclaredField("y");
            fy.setAccessible(true);
            fy.setDouble(boat, y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
