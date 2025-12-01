package fr.ensicaen.genielogiciel.project.model;

public class RecoveryState implements IBoatState {

    IBoat _boat;

    public RecoveryState( IBoat boat ) {
        _boat = boat;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                boat.setInCollision(false);
                _boat.setState(new NormalState(_boat));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void accelerate( IDecoratorEquipment crew, IDecoratorSail sail, double adjustedAngle, double polarSpeed, boolean inCollision ) {
        if (sail != null) polarSpeed = sail.adjustSpeed(polarSpeed, adjustedAngle);
        if (crew != null) polarSpeed = crew.adjustSpeed(polarSpeed, adjustedAngle);
        _boat.setTargetSpeed(polarSpeed);
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
        return "recovery";
    }
}