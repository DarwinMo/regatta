package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

public class ConcreteGoLeftCommande extends AbstractChangeOrientationCommande {


    public ConcreteGoLeftCommande(IBoat boat, int rotationAngle) {
        super(boat, rotationAngle);
    }

    @Override
    public void execute() {
        _boat.rotate(_rotationAngle*-1);
    }
}
