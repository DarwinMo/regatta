package fr.ensicaen.genielogiciel.project.model;


public record DownState(IBoat _boat) implements IBoatState {

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
                    System.err.println("Error: " + e.getMessage());
                    e.printStackTrace(System.err);
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
