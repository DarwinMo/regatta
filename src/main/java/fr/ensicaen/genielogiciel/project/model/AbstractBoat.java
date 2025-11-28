package fr.ensicaen.genielogiciel.project.model;

public abstract class AbstractBoat implements IBoat {

    private double _x;
    private double _y;
    private double _dx = 0;
    private double _dy = 0;
    private int _angle = 0;
    private double _speed = 0;
    private double _windAngle = 0;
    private double _windSpeed = 0;
    private double _targetSpeed = 0;
    private IBoatState _state;
    private IDecoratorSail _sail;
    private IDecoratorEquipment _crew;
    protected PolarTable _polarTable;
    private Boolean _inCollision;

    private static final double ACCELERATION_FACTOR = 0.15;
    private static final double DECELERATION_FACTOR = 0.10;

    public AbstractBoat( double x, double y, IDecoratorSail sail, IDecoratorEquipment crew ) {
        _x = x;
        _y = y;
        _sail = sail;
        _crew = crew;
        _state = new NormalState(this);
        _inCollision = false;
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
    public void applyWind() {
        System.out.println(_state);
        double factor = (_targetSpeed > _speed) ? ACCELERATION_FACTOR : DECELERATION_FACTOR;
        _state.applyWind(_targetSpeed, _speed, _angle, factor);
    }

    @Override
    public void rotate( int deltaAngle ) {
        _angle = (360 + _angle + deltaAngle) % 360;
    }

    private double getAdjustedAngle() {
        double relativeAngle = Math.abs((_angle - _windAngle + 360) % 360);
        if (relativeAngle > 180) {
            relativeAngle = 360 - relativeAngle;
        }
        double adjustedAngle = relativeAngle;
        if (_sail != null) adjustedAngle = _sail.adjustAngle(adjustedAngle);
        if (_crew != null) adjustedAngle = _crew.adjustAngle(adjustedAngle);
        return adjustedAngle;
    }

    public void setInCollision( Boolean inCollision ) {
        _inCollision = inCollision;
    }

    @Override
    public void accelerate() {

        if (_polarTable == null) {
            System.err.println("[ERROR] PolarTable non chargée !");
            return;
        }
        _state.accelerate(_crew, _sail, getAdjustedAngle(), _polarTable.getSpeed(getAdjustedAngle(), _windSpeed), _inCollision);
    }

    @Override
    public void setTargetSpeed( double polarSpeed ) {
        _targetSpeed = polarSpeed;
    }

    // TODO : to implement and use in the presenter
    @Override
    public boolean hasCrossedFinishLine() {
        return false;
    }

    public void setState( IBoatState newState ) {
        _state = newState;
    }

    @Override
    public void setDX( double dx ) {
        _dx = dx;
    }

    @Override
    public void setDY( double dy ) {
        _dy = dy;
    }

    @Override
    public void setX( double x ) {
        _x = x;
    }

    @Override
    public void setY( double y ) {
        _y = y;
    }

    @Override
    public void setSpeed( double speed ) {
        _speed = speed;
    }
}
