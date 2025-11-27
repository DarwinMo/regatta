package fr.ensicaen.genielogiciel.project.model;

public class Line {
    private final double _x1, _y1;
    private final double dx, dy;

    private final double nx, ny;

    private double lastSide = Double.NaN;

    public Line( double x0, double y0, double dirX, double dirY ) {
        this._x1 = x0;
        this._y1 = y0;

        double len = Math.sqrt(dirX * dirX + dirY * dirY);
        this.dx = dirX / len;
        this.dy = dirY / len;

        this.nx = this.dy;
        this.ny = -this.dx;
    }

    private double side( IBoat boat ) {
        double bx = boat.get_x() - _x1;
        double by = boat.get_y() - _y1;
        return bx * nx + by * ny;
    }

    public boolean isCrossedBy( IBoat boat ) {
        double currentSide = side(boat);
        boolean crossed = false;

        if (!Double.isNaN(lastSide) && lastSide * currentSide < 0) {
            double vectorX = boat.get_x() - _x1;
            double vectorY = boat.get_y() - _y1;

            double positionOnLine = vectorX * dx + vectorY * dy;
            if (positionOnLine > 0) {
                crossed = true;
            }
        }

        lastSide = currentSide;
        return crossed;
    }

    public double getStartX() {
        return _x1;
    }

    public double getStartY() {
        return _y1;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}