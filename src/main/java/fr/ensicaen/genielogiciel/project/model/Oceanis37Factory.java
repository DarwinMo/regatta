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
    public IBoat createBoat() throws IOException {
        return new Oceanis37Boat(_sail, _crew);
    }
}
