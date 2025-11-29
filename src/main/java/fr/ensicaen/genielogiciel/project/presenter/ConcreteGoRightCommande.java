package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;
import javafx.animation.Timeline;

public class ConcreteGoRightCommande extends AbstractChangeOrientationCommande {

    public ConcreteGoRightCommande( double rotationAngle, long timeline ) {
        super(rotationAngle, timeline);
    }

    @Override
    public void execute( IBoat boat ) {
        boat.rotate((int) super.getRotationAngle());
    }
}
