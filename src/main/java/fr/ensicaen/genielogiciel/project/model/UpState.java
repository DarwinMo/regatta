package fr.ensicaen.genielogiciel.project.model;

public class UpState implements IBoatState {

    @Override
    public void start( IBoat boat ) {
    }

    @Override
    public void stop( IBoat boat ) {
        boat.setState(new DownState());
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
