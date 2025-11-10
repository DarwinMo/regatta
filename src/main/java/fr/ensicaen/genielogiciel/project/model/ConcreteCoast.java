package fr.ensicaen.genielogiciel.project.model;

public class ConcreteCoast implements ICoast {

    private final double startX;
    private final double startY;
    private final double endX;
    private final double endY;

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

        double lengthCoast = dx * dx + dy * dy;
        if (lengthCoast == 0) return false;

        double t = ((boatX - startX) * dx + (boatY - startY) * dy) / lengthCoast;
        t = Math.max(0, Math.min(1, t));

        double projX = startX + t * dx;
        double projY = startY + t * dy;

        double distance = Math.sqrt(Math.pow(projX - boatX, 2) + Math.pow(projY - boatY, 2));
        return distance < 0.0001;
    }
}