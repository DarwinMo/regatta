package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

public class ConcreteGoLeftCommand extends abstractChangeOrientationCommand {


    public ConcreteGoLeftCommand( double rotationAngle ) {
        super(rotationAngle);
    }

    @Override
    public void execute( IBoat boat ) {
        boat.rotate((int) super.getRotationAngle() * -1);
    }
}
