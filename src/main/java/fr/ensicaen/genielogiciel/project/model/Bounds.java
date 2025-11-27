package fr.ensicaen.genielogiciel.project.model;

public record Bounds(double minX, double minY, double maxX, double maxY) {
    public boolean contains( double x, double y ) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

}
