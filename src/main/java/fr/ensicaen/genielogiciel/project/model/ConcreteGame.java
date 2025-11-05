package fr.ensicaen.genielogiciel.project.model;

public class ConcreteGame implements IGame {
    private IBoat _boat;


    public ConcreteGame() {

    }

    public void setBoat(IBoat boat) {
        this._boat = boat;
    }

    public IBoat getBoat() {
        return _boat;
    }


}
