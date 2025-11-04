package fr.ensicaen.genielogiciel.project.model;

public class BoatModel {
    private double _angle;
    private double _x;
    private double _y;
    private double _dx;
    private double _dy;

    public void BoatModel() {
        _angle = 0.0;
        _x = 0.0;
        _y = 0.0;
    }

    public void rotate(double rotationAngle) {
        _angle += rotationAngle;
        normalizeAngle();
    }

    private void normalizeAngle() {
        if (_angle < 0) {
            _angle += 360;
        } else if (_angle >= 360) {
            _angle -= 360;
        }
    }

    public void changeDirection() {
        double rad = Math.toRadians(_angle);
        _dx = Math.sin(rad);
        _dy = -Math.cos(rad);
        _x += _dx;
        _y += _dy;
    }

    public double getAngle() { return _angle;}
    public double getX() { return _x;}
    public double getY() { return _y;}
    public double getDx() { return _dx;}
    public double getDy() { return _dy;}
}