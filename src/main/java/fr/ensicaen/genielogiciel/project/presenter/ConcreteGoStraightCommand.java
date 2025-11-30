package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

public class ConcreteGoStraightCommand extends abstractChangeOrientationCommand {

    public ConcreteGoStraightCommand( long timeline ) {
        super(0);
    }

    @Override
    public void execute( IBoat boat ) {
        boat.accelerate();
        boat.applyWind();
    }
}
