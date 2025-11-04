package fr.ensicaen.genielogiciel.project.model;

public class ConcreteWind implements IWind {

    private final double _windSpeed;
    private final double _windDirection;

    ConcreteWind(IWeather _weatherAPI){
        _windSpeed = _weatherAPI.getWindSpeed();
        _windDirection = _weatherAPI.getWindDirection();
    }

    @Override
    public double getWindSpeed() {
        return _windSpeed;
    }

    @Override
    public double getWindDirection() {
        return _windDirection;
    }
}
