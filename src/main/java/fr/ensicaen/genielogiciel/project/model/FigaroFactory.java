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
    public IBoat createFigaro() throws IOException {
        return new FigaroBoat(_sail,_crew);
    }
}
