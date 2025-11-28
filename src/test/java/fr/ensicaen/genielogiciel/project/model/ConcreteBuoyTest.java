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
            private final double x = 0;
            private final double y = 0;

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
            public void setState( IBoatState upState ) {

            }

            @Override
            public void setTargetSpeed( double polarSpeed ) {

            }

            @Override
            public void setInCollision( Boolean inCollision ) {

            }

            @Override
            public void setDX( double dX ) {

            }

            @Override
            public void setDY( double dY ) {

            }

            @Override
            public void setX( double x ) {

            }

            @Override
            public void setY( double y ) {

            }

            @Override
            public void setSpeed( double speed ) {

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

    /*@Test
    void testPassageValidation() {
        setBoatPosition(0, 0);
        assertFalse(buoy.checkPassage(boat), "1. Le passage ne devrait pas être validé à l'initialisation.");
    
        setBoatPosition(100, 90);
        assertFalse(buoy.checkPassage(boat), "2. Le bateau est avant la ligne (y=100), ne devrait pas valider.");
    
        setBoatPosition(100, 110);
        assertTrue(buoy.checkPassage(boat), "3. Le bateau devrait valider le franchissement de la ligne.");
    
        setBoatPosition(100, 120);
        assertFalse(buoy.checkPassage(boat), "4. Ne devrait pas re-valider car la bouée est déjà validée.");
    
        setBoatPosition(50, 50);
        assertFalse(buoy.checkPassage(boat), "5. La bouée étant déjà validée, aucune autre validation ne doit avoir lieu.");
    }
    
    @Test
    void testCollision() {
        setBoatPosition(0, 0);
        assertFalse(buoy.checkCollision(boat), "Ne devrait pas détecter de collision à distance (141.4 > 15).");
    
        setBoatPosition(105, 100);
        assertTrue(buoy.checkCollision(boat), "Devrait détecter une collision (distance 5 <= 15).");
    
        setBoatPosition(115, 100);
        assertTrue(buoy.checkCollision(boat), "Devrait détecter une collision à la limite (distance 15).");
    
        setBoatPosition(116, 100);
        assertFalse(buoy.checkCollision(boat), "Ne devrait pas détecter de collision (distance 16 > 15).");
    }*/

    private void setBoatPosition( double x, double y ) {
        try {
            java.lang.reflect.Field fx = boat.getClass().getDeclaredField("x");
            fx.setAccessible(true);
            fx.setDouble(boat, x);

            java.lang.reflect.Field fy = boat.getClass().getDeclaredField("y");
            fy.setAccessible(true);
            fy.setDouble(boat, y);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification de la position du bateau mock via réflexion. Assurez-vous que le mock IBoat a bien les champs 'x' et 'y'.", e);
        }
    }
}