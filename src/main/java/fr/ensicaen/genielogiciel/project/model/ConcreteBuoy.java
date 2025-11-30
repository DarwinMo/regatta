package fr.ensicaen.genielogiciel.project.model;

public class ConcreteBuoy implements IBuoy {

    private final double _x;
    private final double _y;

    private boolean _validated = false;
    private final Line line;
    private double lastSide = Double.NaN;


    @Override
    public double get_x() {
        return _x;
    }

    @Override
    public double get_y() {
        return _y;
    }

    public ConcreteBuoy( double x, double y, double angle ) {
        this._x = x;
        this._y = y;
        double _length = 100;
        this.line = new Line(x, y, angle, _length);
    }

    public double getStartLineX() {
        return line.getStartX();
    }

    public double getStartLineY() {
        return line.getStartY();
    }

    public double getEndLineX() {
        return line.getEndX();
    }

    public double getEndLineY() {
        return line.getEndY();
    }

    private double side( IBoat boat ) {

        double bx = boat.get_x() - line.getStartX();
        double by = boat.get_y() - line.getStartY();
        return bx * line.getNX() + by * line.getNY();
    }

    public boolean isCrossedBy( IBoat boat ) {
        double currentSide = side(boat);
        boolean crossed = false;

        if (!Double.isNaN(lastSide) && lastSide * currentSide < 0) {
            double vectorX = boat.get_x() - line.getStartX();
            double vectorY = boat.get_y() - line.getStartY();

            double positionOnLine = vectorX * line.getDX() + vectorY * line.getDY();
            if (positionOnLine > 0) {
                crossed = true;
            }
        }
        lastSide = currentSide;
        return crossed;
    }


    @Override
    public void checkPassage( IBoat boat ) {
        if (!_validated && isCrossedBy(boat)) {
            _validated = true;
        }
    }

    @Override
    public boolean checkCollision( IBoat boat ) {
        double dx = boat.get_x() - _x;
        double dy = boat.get_y() - _y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= 15;
    }

    @Override
    public boolean isMarked() {
        return _validated;
    }

    @Override
    public void setMarked( boolean marked ) {
        _validated = marked;
    }
}