package fr.ensicaen.genielogiciel.project.model;

public class ConcreteBuoy implements IBuoy {

    private final double _x;
    private final double _y;
    private final double _width = 20;

    private boolean _entryPassed = false;

    private static class Line {
        double _x1, _y1, _x2, _y2;

        public Line( double _x1, double y1, double x2, double y2 ) {
            this._x1 = _x1;
            this._y1 = y1;
            this._x2 = x2;
            this._y2 = y2;
        }

        public boolean isCrossedBy( IBoat boat ) {
            double minX = Math.min(_x1, _x2);
            double maxX = Math.max(_x1, _x2);
            double minY = Math.min(_y1, _y2);
            double maxY = Math.max(_y1, _y2);
            return boat.get_x() >= minX && boat.get_x() <= maxX && boat.get_y() >= minY && boat.get_y() <= maxY;
        }

    }

    private final Line lineEntry, lineExit;

    @Override
    public double get_x() {
        return _x;
    }

    @Override
    public double get_y() {
        return _y;
    }

    @Override
    public boolean isMarked() {
        return false;
    }

    public ConcreteBuoy( double x, double y, double angle ) {
        this._x = x;
        this._y = y;
        double offsetX = Math.cos(Math.toRadians(angle + 90)) * _width;
        double offsetY = Math.sin(Math.toRadians(angle + 90)) * _width;

        this.lineEntry = new Line(x - offsetX, y - offsetY, x + offsetX, y + offsetY);
        this.lineExit = new Line(x - offsetX * 0.9, y - offsetY * 0.9, x + offsetX * 0.9, y + offsetY * 0.9);
    }

    public boolean checkPassage( IBoat boat ) {
        if (!_entryPassed && lineEntry.isCrossedBy(boat)) {
            _entryPassed = true;
            return true;
        } else if (_entryPassed && lineExit.isCrossedBy(boat)) {
            return true;
        }
        return false;
    }

    public boolean checkCollision( IBoat boat ) {
        double dx = boat.get_x() - _x;
        double dy = boat.get_y() - _y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double radius = 15; // rayon de collision
        return distance <= radius;
    }
}

