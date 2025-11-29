package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

public class ConcreteGoStraightCommande extends AbstractChangeOrientationCommande {

    public ConcreteGoStraightCommande( long timeline ) {
        super(0, timeline);
    }

    @Override
    public void execute( IBoat boat ) {
        boat.accelerate();
        boat.applyWind();
    }
}
