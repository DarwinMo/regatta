package fr.ensicaen.genielogiciel.project.model;


public class DownState implements IBoatState {

    private IBoat _boat;

    public DownState( IBoat boat ) {
        _boat = boat;
    }

    @Override
    public void accelerate( IDecoratorEquipment crew, IDecoratorSail sail, double adjustedAngle, double polarSpeed, boolean inCollision ) {
        if (inCollision) {
            _boat.set_speed(0);
            _boat.setDX(0);
            _boat.setDY(0);
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    _boat.setInCollision(false);
                    _boat.setState(new NormalState(_boat));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void applyWind( double targetSpeed, double speed, double angle, double factor ) {
        _boat.setDX(0);
        _boat.setDY(0);
    }

    public String toString() {
        return "down";
    }
}
