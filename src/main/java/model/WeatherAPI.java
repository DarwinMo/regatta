package model;

import java.util.Random;

public class WeatherAPI implements IWeather {

    private double _tempWindSpeed;
    private double _tempDirection;
    private final Random _random;


    public WeatherAPI() {
        _random = new Random();
    }

    @Override
    public double getWindSpeed() {
        double maxWindSpeed = 30.0;
        _tempWindSpeed = _random.nextDouble() * maxWindSpeed;
        return _tempWindSpeed;
    }

    @Override
    public double getWindDirection() {
        _tempDirection = _random.nextDouble() *  360;
        return _tempDirection;
    }

    public double getTempWindSpeed() {
        return _tempWindSpeed;
    }

    public double getTempDirection() {
        return _tempDirection;
    }
}
