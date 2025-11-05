package fr.ensicaen.genielogiciel.project.model;

public class Bounds {
    private final double minX;
    private final double minY;
    private final double maxX;
    private final double maxY;

    public Bounds(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }


    public boolean contains(double x, double y) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }


    public double getMinX() {
        return minX;
    }
    public double getMinY() {
        return minY;
    }
    public double getMaxX() {
        return maxX;
    }
    public double getMaxY() {
        return maxY;
    }

}
