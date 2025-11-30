package fr.ensicaen.genielogiciel.project.model;

public record FigaroFactory(IDecoratorSail _sail, IDecoratorEquipment _crew) implements IBoatFactory {

    @Override
    public IBoat createBoat( double x, double y, double windSpeed, double windDirection ) {
        FigaroBoat boat = new FigaroBoat(x, y, _sail, _crew);
        boat.set_windAngle(windDirection);
        boat.set_windSpeed(windSpeed);
        return boat;
    }
}
