package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;
import javafx.animation.Timeline;

public abstract class AbstractChangeOrientationCommande {
    private final double _rotationAngle;
    private final long _timeline;

    public AbstractChangeOrientationCommande( double rotationAngle, long timeline ) {
        _rotationAngle = rotationAngle;
        _timeline = timeline;
    }

    public double getRotationAngle() {
        return _rotationAngle;
    }

    public long getTimeStamp() {
        return _timeline;
    }

    public abstract void execute( IBoat boat );
}
