package fr.ensicaen.genielogiciel.project.model;

import java.io.IOException;

public class Oceanis37Factory implements IBoatFactory {

    private final IDecoratorSail _sail;
    private final IDecoratorEquipment _crew;

    public Oceanis37Factory( IDecoratorSail sail, IDecoratorEquipment crew ) {
        _sail = sail;
        _crew = crew;
    }

    @Override
    public IBoat createBoat( double x, double y, double windSpeed, double windDirection ) throws IOException {
        Oceanis37Boat boat = new Oceanis37Boat(x, y, _sail, _crew);
        boat.set_windAngle(windDirection);
        boat.set_windSpeed(windSpeed);
        return boat;
    }
}
