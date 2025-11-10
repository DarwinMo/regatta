package fr.ensicaen.genielogiciel.project.model;

public final class BoatModel {
    private double _x = 580;
    private double _y = 480;
    private double _dx = 0;
    private double _dy = 0;
    private int _anglePositive = 0;

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    public void rotate( int angle ) {
        _anglePositive = (360 + _anglePositive + angle) % 360;
    }

    public double getAngle() {
        return _anglePositive;
    }

    public double getDx() {
        return _dx;
    }

    public double getDy() {
        return _dy;
    }

    public void move() {
        _dx = Math.sin(_anglePositive * Math.PI / 180);
        _dy = -Math.cos(_anglePositive * Math.PI / 180);
        _x += _dx;
        _y += _dy;
    }
}
