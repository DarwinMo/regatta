package fr.ensicaen.genielogiciel.project.model;


public record DownState(IBoat _boat) implements IBoatState {


    @Override
    public void accelerate( IDecoratorEquipment crew, IDecoratorSail sail, double adjustedAngle, double polarSpeed, boolean inCollision ) {
        _boat.setTargetSpeed(0);
        _boat.setState(new CoolDownState(_boat));
    }

    @Override
    public void applyWind( double targetSpeed, double speed, double angle, double factor ) {
        _boat.setSpeed(0);
        _boat.setDX(0);
        _boat.setDY(0);
    }

    public String toString() {
        return "down";
    }
}