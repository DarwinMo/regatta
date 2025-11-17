package fr.ensicaen.genielogiciel.project.model;

import java.util.Random;

public class WeatherAPI implements IWeather {

    private double _tempWindSpeed;
    private double _tempDirection;
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

    public double getTempWindSpeed() {
        return _tempWindSpeed;
    }

    public double getTempDirection() {
        return _tempDirection;
    }
}
