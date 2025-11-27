package fr.ensicaen.genielogiciel.project.model;

public record ConcreteCoast(double startX, double startY, double endX, double endY) implements ICoast {

    private static final double COLLISION_RADIUS = 20.0;

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