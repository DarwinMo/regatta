package fr.ensicaen.genielogiciel.project.model;

public class FigaroBoat implements IBoat {

    private double _x = 580;
    private double _y = 480;
    private double _dx = 0;
    private double _dy = 0;
    private int _angle = 0;
    private double _speed = 0;
    private double _windAngle = 0;
    private double _windSpeed = 0;
    private IDecoratorSail _sail;
    private IDecoratorEquipment _crew;


    public FigaroBoat( IDecoratorSail sail, IDecoratorEquipment crew ) {
        _sail = sail;
        _crew = crew;
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
    public void set_windSpeed( double _windSpeed ) {
        this._windSpeed = _windSpeed;
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
        // Adjust the boat heading slightly to align better with the wind
        int diff = (int) (_windAngle - _angle);
        if (Math.abs(diff) > 5) rotate((diff > 0) ? 2 : -2);
    }

    @Override
    public void accelerate() {
        // Compute base speed based on wind intensity
        double baseSpeed = _windSpeed * 0.1;

        // Calculate the relative angle between wind direction and boat heading
        double relativeAngle = Math.abs((_angle - _windAngle + 360) % 360);
        if (relativeAngle > 180) relativeAngle = 360 - relativeAngle;

        // Adjust angle depending on sail and crew characteristics
        double adjustedAngle = relativeAngle;
        double adjustedSpeed = baseSpeed;
        if (_sail != null) {
            adjustedAngle = _sail.adjustAngle(adjustedAngle);
            adjustedSpeed = _sail.adjustSpeed(adjustedSpeed, adjustedAngle);
        }
        if (_crew != null) {
            adjustedAngle = _crew.adjustAngle(adjustedAngle);
            adjustedSpeed = _crew.adjustSpeed(adjustedSpeed, adjustedAngle);
        }

        // Compute speed using a simplified polar curve model
        double polarFactor = Math.max(0.3, Math.cos(Math.toRadians(adjustedAngle)));
        _speed = adjustedSpeed * (1.5 + polarFactor);

        // à enlever par la suite
        System.out.printf(
                "[DEBUG] Angle=%.1f° (adjusted=%.1f°) | BaseSpeed=%.2f | FinalSpeed=%.2f", relativeAngle, adjustedAngle, baseSpeed, _speed
        );
    }

    @Override
    public void applyWind() {
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
