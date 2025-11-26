package fr.ensicaen.genielogiciel.project.model;

public class ConcreteBuoy implements IBuoy {

    private final double _x;
    private final double _y;

    private boolean _validated = false;
    private final Line line;

    @Override
    public double get_x() { return _x; }

    @Override
    public double get_y() { return _y; }

    @Override
    public boolean isMarked() {
        return _validated;
    }

    public ConcreteBuoy( double x, double y, double angle ) {
        this._x = x;
        this._y = y;

        double radLine = Math.toRadians(angle + 90);
        double dx = Math.cos(radLine);
        double dy = Math.sin(radLine);

        double radCourse = Math.toRadians(angle);
        double courseX = Math.cos(radCourse);
        double courseY = Math.sin(radCourse);

        double dotProduct = dy * courseX + (-dx) * courseY;

        if (dotProduct < 0) {
            dx = -dx;
            dy = -dy;
        }

        this.line = new Line(x, y, dx, dy);
    }

    public Line getLine() { return line; }

    @Override
    public boolean checkPassage(IBoat boat) {
        if (!_validated && line.isCrossedBy(boat)) {
            _validated = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkCollision(IBoat boat) {
        double dx = boat.get_x() - _x;
        double dy = boat.get_y() - _y;
        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance <= 15;
    }
}