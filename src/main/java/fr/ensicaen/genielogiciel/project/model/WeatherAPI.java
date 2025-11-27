package fr.ensicaen.genielogiciel.project.model;

public class WeatherAPI implements IWeather {

    private double _tempWindSpeed;
    private final double _tempDirection;
    private final PrevisionMeteoAPI _previsionAPI;

    private static double kmhToKnots( double kmh ) {
        return kmh / 1.852;
    }

    public WeatherAPI() {
        _previsionAPI = new PrevisionMeteoAPI();
        _tempDirection = _previsionAPI.getWindDirection();
        _tempWindSpeed = _previsionAPI.getWindSpeed();
        _tempWindSpeed = kmhToKnots(_tempWindSpeed);
    }

    @Override
    public double getWindSpeed() {
        return _tempWindSpeed;
    }

    @Override
    public double getWindDirection() {
        return _tempDirection;
    }

}
