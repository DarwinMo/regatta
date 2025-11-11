package fr.ensicaen.genielogiciel.project.model;

import java.util.Random;

public class WeatherAPI implements IWeather {

    private double _tempWindSpeed;
    private double _tempDirection;
    private final PrevisionMeteoAPI _previsionAPI;


    public WeatherAPI() {
        _previsionAPI = new PrevisionMeteoAPI();
        _tempDirection = _previsionAPI.getWindDirection();
        _tempWindSpeed = _previsionAPI.getWindSpeed();
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
