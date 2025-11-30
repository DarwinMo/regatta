package fr.ensicaen.genielogiciel.project.model;


public record Oceanis37Factory(IDecoratorSail _sail, IDecoratorEquipment _crew) implements IBoatFactory {

    @Override
    public IBoat createBoat( double x, double y, double windSpeed, double windDirection ) {
        Oceanis37Boat boat = new Oceanis37Boat(x, y, _sail, _crew);
        boat.set_windAngle(windDirection);
        boat.set_windSpeed(windSpeed);
        return boat;
    }
}
