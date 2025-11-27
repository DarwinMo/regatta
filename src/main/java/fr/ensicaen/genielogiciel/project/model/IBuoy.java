package fr.ensicaen.genielogiciel.project.model;

public interface IBuoy {
    double get_x();

    double get_y();

    boolean isMarked();


    boolean checkPassage( IBoat boat );

    boolean checkCollision( IBoat boat );

    public double getStartLineX();

    public double getStartLineY();

    public double getEndLineX();

    public double getEndLineY();
}
