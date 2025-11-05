package fr.ensicaen.genielogiciel.project.model;

public class ConcreteCoast implements ICoast {
    private Bounds _coastBounds;

    public ConcreteCoast(double minX, double minY, double maxX, double maxY) {
        _coastBounds = new Bounds(minX, minY, maxX, maxY);
    }

    @Override
    public boolean isInCollision(double x, double y) {
        return _coastBounds.contains(x, y);

    }
}
