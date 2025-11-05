package fr.ensicaen.genielogiciel.project.model;

public class ConcreteWaterPlan implements IWaterPlan {
    private Bounds _bounds;
    private IWind _wind;

    @Override
    public boolean isInside(double x, double y) {
        return _bounds.contains(x, y);
    }

    public ConcreteWaterPlan(double width, double height, IWind wind) {
        _wind = wind;
        _bounds = new Bounds(0.0, 0.0, width, height);
    }

    @Override
    public Bounds getBounds() {
        return _bounds;
    }

    @Override
    public IWind getWind() {
        return _wind;
    }

}
