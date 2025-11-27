package fr.ensicaen.genielogiciel.project.model;

public class Line {
    private final double _sX, _sY, _eX, _eY;

    private final double _dX, _dY, _nx, _ny;


    public Line( double x0, double y0, double angle, double length ) {
        this._sX = x0;
        this._sY = y0;

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

        _dX = dx / length;
        _dY = dy / length;
        _eX = _sX + dx * length;
        _eY = _sY + dy * length;
        _nx = _dY;
        _ny = -_dX;
    }


    public double getStartX() {
        return _sX;
    }

    public double getStartY() {
        return _sY;
    }

    public double getEndX() {
        return _eX;
    }

    public double getEndY() {
        return _eY;
    }

    public double getDX() {
        return _dX;
    }

    public double getDY() {
        return _dY;
    }

    public double getNX() {
        return _nx;
    }

    public double getNY() {
        return _ny;
    }

}