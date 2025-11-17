package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

public class ConcreteGoRightCommande extends AbstractChangeOrientationCommande {

    public ConcreteGoRightCommande( IBoat boat, int rotationAngle ) {
        super(boat, rotationAngle);
    }

    @Override
    public void execute() {
        _boat.rotate(_rotationAngle);
    }
}
