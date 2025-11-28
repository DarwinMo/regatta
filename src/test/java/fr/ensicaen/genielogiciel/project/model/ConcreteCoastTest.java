package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConcreteCoastTest {

    static class TestBoat implements IBoat {
        private final double _x;
        private final double _y;
        private IBoatState _state;

        public TestBoat( double x, double y ) {
            _x = x;
            _y = y;
            _state = new NormalState(this);
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
        public boolean hasCrossedFinishLine() {
            return false;
        }

        @Override
        public void setState( IBoatState newState ) {
            _state = newState;
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
    }

    @Test
    void testCollisionOnCoast() {
        ConcreteCoast coast = new ConcreteCoast(0, 0, 10, 0);

        TestBoat boatOnCoast = new TestBoat(5, 0);
        assertTrue(coast.isCollision(boatOnCoast), "Le bateau devrait toucher la côte (distance 0)");

        TestBoat boatJustColliding = new TestBoat(5, 15);
        assertTrue(coast.isCollision(boatJustColliding), "Le bateau devrait toucher la côte (distance 15)");


        TestBoat boatNearCoast = new TestBoat(5, 20.1);
        assertFalse(coast.isCollision(boatNearCoast), "Le bateau ne devrait pas toucher la côte (distance 20.1)");

        TestBoat boatFar = new TestBoat(5, 30);
        assertFalse(coast.isCollision(boatFar), "Le bateau est trop loin de la côte (distance 30)");

        TestBoat boatStart = new TestBoat(0, 0);
        TestBoat boatEnd = new TestBoat(10, 0);
        assertTrue(coast.isCollision(boatStart), "Le bateau touche l'extrémité start");
        assertTrue(coast.isCollision(boatEnd), "Le bateau touche l'extrémité end");

        TestBoat boatNearStart = new TestBoat(-15, 0);
        assertTrue(coast.isCollision(boatNearStart), "Le bateau touche l'extrémité start (distance 15)");

        TestBoat boatFarFromStart = new TestBoat(-20.1, 0);
        assertFalse(coast.isCollision(boatFarFromStart), "Le bateau ne touche pas l'extrémité start (distance 20.1)");
    }

    @Test
    void testZeroLengthCoast() {
        ConcreteCoast coast = new ConcreteCoast(5, 5, 5, 5);

        TestBoat boatAtPoint = new TestBoat(5, 5);
        assertTrue(coast.isCollision(boatAtPoint), "Le bateau au point devrait détecter une collision");

        TestBoat boatNearPoint = new TestBoat(5, 5 + 19);
        assertTrue(coast.isCollision(boatNearPoint), "Le bateau près du point devrait détecter une collision");

        TestBoat boatFarFromPoint = new TestBoat(5, 5 + 21);
        assertFalse(coast.isCollision(boatFarFromPoint), "Le bateau loin du point ne devrait pas détecter de collision");
    }
}