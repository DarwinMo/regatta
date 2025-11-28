package fr.ensicaen.genielogiciel.project.model;

import java.io.IOException;

public class FigaroFactory implements IBoatFactory {

    private final IDecoratorSail _sail;
    private final IDecoratorEquipment _crew;

    public FigaroFactory( IDecoratorSail sail, IDecoratorEquipment crew ) {
        _sail = sail;
        _crew = crew;
    }

    @Override
    public IBoat createBoat( double x, double y, double windSpeed, double windDirection ) throws IOException {
        FigaroBoat boat = new FigaroBoat(x, y, _sail, _crew);
        boat.set_windAngle(windDirection);
        boat.set_windSpeed(windSpeed);
        return boat;
    }
}
