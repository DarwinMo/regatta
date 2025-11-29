package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;
import javafx.animation.Timeline;

public class ConcreteGoLeftCommande extends AbstractChangeOrientationCommande {


    public ConcreteGoLeftCommande( double rotationAngle, long timeline ) {
        super(rotationAngle, timeline);
    }

    @Override
    public void execute( IBoat boat ) {
        boat.rotate((int) super.getRotationAngle() * -1);
    }
}
