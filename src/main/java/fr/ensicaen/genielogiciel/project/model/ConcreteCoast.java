package fr.ensicaen.genielogiciel.project.model;

public class ConcreteCoast implements ICoast {

    private final double startX;
    private final double startY;
    private final double endX;
    private final double endY;
    private static final double COLLISION_RADIUS = 20.0;

    public ConcreteCoast( double startX, double startY, double endX, double endY ) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public double getStartX() {
        return startX;
    }

    @Override
    public double getStartY() {
        return startY;
    }

    @Override
    public double getEndX() {
        return endX;
    }

    @Override
    public double getEndY() {
        return endY;
    }

    @Override
    public boolean isCollision( IBoat boat ) {
        double boatX = boat.get_x();
        double boatY = boat.get_y();

        double dx = endX - startX;
        double dy = endY - startY;

        double lengthCoastSq = dx * dx + dy * dy;
        if (lengthCoastSq == 0) {
            double distSq = Math.pow(boatX - startX, 2) + Math.pow(boatY - startY, 2);
            return distSq < (COLLISION_RADIUS * COLLISION_RADIUS);
        }
        double t = ((boatX - startX) * dx + (boatY - startY) * dy) / lengthCoastSq;

        t = Math.max(0, Math.min(1, t));

        double projX = startX + t * dx;
        double projY = startY + t * dy;

        double distSq = Math.pow(projX - boatX, 2) + Math.pow(projY - boatY, 2);

        return distSq < (COLLISION_RADIUS * COLLISION_RADIUS);
    }
}