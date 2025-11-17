package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

public abstract class AbstractChangeOrientationCommande {
    protected IBoat _boat;
    protected int _rotationAngle;

    public AbstractChangeOrientationCommande( IBoat boat, int rotationAngle ) {
        _boat = boat;
        _rotationAngle = rotationAngle;
    }

    public abstract void execute();
}
