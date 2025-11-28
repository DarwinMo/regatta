package fr.ensicaen.genielogiciel.project.model;

public interface IBuoy {
    double get_x();

    double get_y();

    void checkPassage( IBoat boat );

    boolean checkCollision( IBoat boat );

    double getStartLineX();

    double getStartLineY();

    double getEndLineX();

    double getEndLineY();

    boolean isMarked();
}
