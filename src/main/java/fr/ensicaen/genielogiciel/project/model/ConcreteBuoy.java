package fr.ensicaen.genielogiciel.project.model;

public class ConcreteBuoy implements IBuoy {

    double x;
    double y;

    @Override
    public double getX() {
        return x;
    }

    @Override
    public boolean isMarked() {
        return false;
    }

    @Override
    public double getY() {
        return y;
    }


    public ConcreteBuoy( double x, double y ) {
        this.x = x;
        this.y = y;
    }
}
