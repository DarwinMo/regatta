package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

public abstract class abstractChangeOrientationCommand {
    private final double _rotationAngle;

    public abstractChangeOrientationCommand( double rotationAngle ) {
        _rotationAngle = rotationAngle;
    }

    public double getRotationAngle() {
        return _rotationAngle;
    }

    public abstract void execute( IBoat boat );
}
