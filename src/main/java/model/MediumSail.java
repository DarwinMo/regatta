package model;

public class MediumSail implements IDecoratorSail{
    @Override
    public double adjustSpeed(double baseSpeed, double windAngle) {
        double adjstedSpeed = baseSpeed;
        if (windAngle <= 90) {
            adjstedSpeed *= 1.1;
        } else {
            adjstedSpeed *= 0.9;
        }
        return adjstedSpeed;
    }
}
