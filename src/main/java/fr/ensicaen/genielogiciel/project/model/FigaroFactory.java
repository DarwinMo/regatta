package fr.ensicaen.genielogiciel.project.model;

import java.io.IOException;

public class FigaroFactory implements IBoatFactory {

    private final IDecoratorSail _sail;
    private final IDecoratorEquipment _crew;
    private IBoatState _state;

    public FigaroFactory( IDecoratorSail sail, IDecoratorEquipment crew ) {
        _sail = sail;
        _crew = crew;
        _state = new UpState();
    }

    @Override
    public IBoat createBoat( double x, double y ) throws IOException {
        return new FigaroBoat(x, y, _sail, _crew);
    }
}
