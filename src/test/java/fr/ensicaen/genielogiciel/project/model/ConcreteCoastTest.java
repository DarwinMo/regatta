package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConcreteCoastTest {

    static class TestBoat implements IBoat {
        private double _x, _y;
        private IBoatState _state;

        public TestBoat( double x, double y ) {
            _x = x;
            _y = y;
            _state = new UpState();
        }

        @Override
        public double get_x() {
            return _x;
        }

        @Override
        public double get_y() {
            return _y;
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
        public void start() {
            _state.start(this);
        }

        @Override
        public void stop() {
            _state.stop(this);
        }

        @Override
        public boolean isRunning() {
            return _state.isRunning();
        }

        @Override
        public boolean hasCrossedFinishLine() {
            return false;
        }

        @Override
        public void setState( IBoatState newState ) {
            _state = newState;
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
    }

    @Test
    void testCollisionOnCoast() {
        ConcreteCoast coast = new ConcreteCoast(0, 0, 10, 0);

        TestBoat boatOnCoast = new TestBoat(5, 0);
        assertTrue(coast.isCollision(boatOnCoast), "Le bateau devrait toucher la côte");

        TestBoat boatNearCoast = new TestBoat(5, 0.1);
        assertFalse(coast.isCollision(boatNearCoast), "Le bateau ne devrait pas toucher la côte");

        TestBoat boatFar = new TestBoat(5, 1);
        assertFalse(coast.isCollision(boatFar), "Le bateau est trop loin de la côte");

        TestBoat boatStart = new TestBoat(0, 0);
        TestBoat boatEnd = new TestBoat(10, 0);
        assertTrue(coast.isCollision(boatStart), "Le bateau touche l'extrémité start");
        assertTrue(coast.isCollision(boatEnd), "Le bateau touche l'extrémité end");
    }

    @Test
    void testZeroLengthCoast() {
        ConcreteCoast coast = new ConcreteCoast(5, 5, 5, 5);
        TestBoat boat = new TestBoat(5, 5);
        assertFalse(coast.isCollision(boat), "Coast de longueur zéro ne doit pas détecter de collision");
    }
}
