package fr.ensicaen.genielogiciel.project.model;

public class Figaro implements IBoat {

    private double x = 580;
    private double y = 480;
    private double dx = 0;
    private double dy = 0;
    private int angle = 0;
    private double speed = 0;
    private double windAngle = 0;
    private double windSpeed = 0;
    private int crewNumber = 1;
    private int sailSize = 0;

    @Override public double getX() { return x; }

    @Override public double getY() { return y; }

    @Override
    public double getDx() { return dx ;}

    @Override
    public double getDy() { return dy; }

    @Override public int getAngle() { return angle; }
    @Override public void setAngle(int angle) { this.angle = angle; }
    @Override public double getSpeed() { return speed; }
    @Override public void setSpeed(double speed) { this.speed = speed; }
    @Override public void setWindAngle(double windAngle) { this.windAngle = windAngle; }
    @Override public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    @Override public void setCrewNumber(int crewNumber) { this.crewNumber = crewNumber; }
    @Override public void setSailSize(int sailSize) { this.sailSize = sailSize; }
    @Override


    public void trim() {
        // Adjust the boat heading slightly to align better with the wind
        int diff = (int) (windAngle - angle);
        if (Math.abs(diff) > 5) rotate((diff > 0) ? 2 : -2);
    }

    @Override
    public void accelerate() {
        // Compute base speed based on wind intensity
        double baseSpeed = windSpeed * 0.1;

        // Calculate the relative angle between wind direction and boat heading
        double relativeAngle = Math.abs((angle - windAngle + 360) % 360);
        if (relativeAngle > 180) relativeAngle = 360 - relativeAngle;

        // Adjust angle depending on sail and crew characteristics
        double adjustedAngle = relativeAngle;
        if (sailSize == 1) {
            if (relativeAngle < 90) adjustedAngle += 4;  // large sail: worse upwind
            else baseSpeed *= 1.1;                       // better downwind
        }
        if (crewNumber == 2) {
            if (relativeAngle < 90) baseSpeed *= 1.1;    // more crew: better upwind
            else baseSpeed *= 0.9;                       // slower downwind
        }

        // Compute speed using a simplified polar curve model
        double polarFactor = Math.max(0.3, Math.cos(Math.toRadians(adjustedAngle)));
        speed = baseSpeed * (1.5 + polarFactor);
    }

    @Override
    public void applyWind() {
        // Apply wind effect to movement based on current angle and speed
        dx = Math.sin(Math.toRadians(angle)) * speed;
        dy = -Math.cos(Math.toRadians(angle)) * speed;
        x += dx;
        y += dy;
    }

    @Override
    public void move( ) {
        dx = Math.sin(angle * Math.PI / 180);
        dy = -Math.cos(angle * Math.PI / 180);
        x += dx;
        y += dy;
    }

    @Override
    public void rotate(int deltaAngle) {
        angle = (360 + angle + deltaAngle) % 360;
    }
}
