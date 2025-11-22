package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

import java.util.ArrayList;
import java.util.List;

public class BoatDirectionManager {
    private final AbstractChangeOrientationCommande _leftCommande;
    private final AbstractChangeOrientationCommande _rightCommande;
    private final List<AbstractChangeOrientationCommande> _history;

    public BoatDirectionManager( IBoat boat, int rotationAngle ) {
        _rightCommande = new ConcreteGoRightCommande(boat, rotationAngle);
        _leftCommande = new ConcreteGoLeftCommande(boat, rotationAngle);
        _history = new ArrayList<>();
    }

    public void goRight() {
        _rightCommande.execute();
        _history.add(_rightCommande);
    }

    public void goLeft() {
        _leftCommande.execute();
        _history.add(_leftCommande);
    }

    public List<AbstractChangeOrientationCommande> getHistory() {
        return _history;
    }
}
