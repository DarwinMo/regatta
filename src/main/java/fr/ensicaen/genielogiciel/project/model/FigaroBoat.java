package fr.ensicaen.genielogiciel.project.model;

import java.io.IOException;
import java.io.InputStream;

public class FigaroBoat implements IBoat {

    private double _x = 580;
    private double _y = 480;
    private double _dx = 0;
    private double _dy = 0;
    private int _angle = 0;
    private double _speed = 0;
    private double _windAngle = 0;
    private double _windSpeed = 0;
    private double _targetSpeed = 0;
    private IDecoratorSail _sail;
    private IDecoratorEquipment _crew;
    private PolarTable _polarTable;

    private static final double ACCELERATION_FACTOR = 0.15;
    private static final double DECELERATION_FACTOR = 0.10;
    private static final String POLAR = "/fr/ensicaen/genielogiciel/project/model/polaire-figaro.pol";

    public FigaroBoat( IDecoratorSail sail, IDecoratorEquipment crew ) {
        _sail = sail;
        _crew = crew;
        InputStream test = getClass().getResourceAsStream(POLAR);
        System.out.println("Test resource = " + (test == null ? "NOT FOUND" : "OK"));

        try {
            _polarTable = new PolarTable(POLAR);
        } catch (IOException e) {
            System.err.println("[WARNING] Impossible de charger la table polaire : " + e.getMessage());
            _polarTable = null;
        }
    }

    @Override
    public double get_x() {
        return _x;
    }

    @Override
    public double get_y() {
        return _y;
    }

    @Override
    public double get_dx() {
        return _dx;
    }

    @Override
    public double get_dy() {
        return _dy;
    }

    @Override
    public int get_angle() {
        return _angle;
    }

    @Override
    public void set_angle( int _angle ) {
        this._angle = _angle;
    }

    @Override
    public double get_speed() {
        return _speed;
    }

    @Override
    public void set_speed( double _speed ) {
        this._speed = _speed;
    }

    @Override
    public void set_windAngle( double _windAngle ) {
        this._windAngle = _windAngle;
    }

    @Override
    public double get_windAngle() {
        return _windAngle;
    }

    @Override
    public void set_windSpeed( double _windSpeed ) {
        this._windSpeed = _windSpeed;
    }

    @Override
    public double get_windSpeed() {
        return _windSpeed;
    }

    @Override
    public void set_crew( IDecoratorEquipment crew ) {
        _crew = crew;
    }

    @Override
    public void set_sail( IDecoratorSail sail ) {
        _sail = sail;
    }

    @Override
    public void trim() {
        int diff = (int) (_windAngle - _angle);
        if (Math.abs(diff) > 5) rotate((diff > 0) ? 2 : -2);
    }

    @Override
    public void accelerate() {
        if (_polarTable == null) {
            System.err.println("[ERROR] PolarTable non chargée !");
            return;
        }

        double relativeAngle = Math.abs((_angle - _windAngle + 360) % 360);
        if (relativeAngle > 180) {
            relativeAngle = 360 - relativeAngle;
        }

        double adjustedAngle = relativeAngle;
        if (_sail != null) adjustedAngle = _sail.adjustAngle(adjustedAngle);
        if (_crew != null) adjustedAngle = _crew.adjustAngle(adjustedAngle);

        double polarSpeed = _polarTable.getSpeed(adjustedAngle, _windSpeed);

        if (_sail != null) polarSpeed = _sail.adjustSpeed(polarSpeed, adjustedAngle);
        if (_crew != null) polarSpeed = _crew.adjustSpeed(polarSpeed, adjustedAngle);

        _targetSpeed = polarSpeed;

        System.out.printf(
                "Angle=%.1f° | Speed=%.2f%n", adjustedAngle, _speed
        );
    }

    @Override
    public void applyWind() {
        double factor = (_targetSpeed > _speed) ? ACCELERATION_FACTOR : DECELERATION_FACTOR;
        _speed += factor * (_targetSpeed - _speed);
        _dx = Math.sin(Math.toRadians(_angle)) * _speed;
        _dy = -Math.cos(Math.toRadians(_angle)) * _speed;
        _x += _dx;
        _y += _dy;
    }

    @Override
    public void move() {
        _dx = Math.sin(_angle * Math.PI / 180);
        _dy = -Math.cos(_angle * Math.PI / 180);
        _x += _dx;
        _y += _dy;
    }

    @Override
    public void rotate( int deltaAngle ) {
        _angle = (360 + _angle + deltaAngle) % 360;
    }
}
