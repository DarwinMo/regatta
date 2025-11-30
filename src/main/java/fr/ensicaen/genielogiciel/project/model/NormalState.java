package fr.ensicaen.genielogiciel.project.model;

public class NormalState implements IBoatState {

    IBoat _boat;

    NormalState( IBoat boat ) {
        _boat = boat;
    }

    @Override
    public void accelerate( IDecoratorEquipment crew, IDecoratorSail sail, double adjustedAngle, double polarSpeed, boolean inCollision ) {
        if (inCollision) {
            _boat.setState(new DownState(_boat));
        } else {
            if (sail != null) polarSpeed = sail.adjustSpeed(polarSpeed, adjustedAngle);
            if (crew != null) polarSpeed = crew.adjustSpeed(polarSpeed, adjustedAngle);
            _boat.setTargetSpeed(polarSpeed);
        }
    }

    @Override
    public void applyWind( double targetSpeed, double speed, double angle, double factor ) {
        speed += factor * (targetSpeed - speed);
        _boat.setSpeed(speed);
        _boat.setDX(Math.sin(Math.toRadians(angle)) * speed);
        _boat.setDY(-Math.cos(Math.toRadians(angle)) * speed);
        _boat.setY(_boat.get_y() + _boat.get_dy());
        _boat.setX(_boat.get_x() + _boat.get_dx());
    }

    public String toString() {
        return "normal";
    }

}
