package fr.ensicaen.genielogiciel.project.model;

public class DownState implements IBoatState {

    @Override
    public void start( IBoat boat ) {
        boat.setState(new UpState());
    }

    @Override
    public void stop( IBoat boat ) {
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
