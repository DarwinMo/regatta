package fr.ensicaen.genielogiciel.project.model;

public record CoolDownState(IBoat _boat) implements IBoatState {

    public CoolDownState {
        startCoolDownTimer(_boat);
    }

    private void startCoolDownTimer( IBoat boat ) {
        new Thread(() -> {
            try {
                Thread.sleep(4000);
                boat.setState(new RecoveryState(_boat));
            } catch (InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace(System.err);
            }
        }).start();
    }

    @Override
    public void accelerate( IDecoratorEquipment crew, IDecoratorSail sail, double adjustedAngle, double polarSpeed, boolean inCollision ) {
        return;
    }

    @Override
    public void applyWind( double targetSpeed, double speed, double angle, double factor ) {
        return;
    }

    public String toString() {
        return "CoolDownState";
    }
}