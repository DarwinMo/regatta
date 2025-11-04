package model;

public class ConcreteWind implements IWind{

    private double windSpeed;
    private double windDirection;


    @Override
    public double getWindSpeed() {
        return 0;
    }

    @Override
    public double getWindDirection() {
        return 0;
    }
}
