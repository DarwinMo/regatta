package fr.ensicaen.genielogiciel.project.model;

public interface IBuoy {
    double get_x();

    double get_y();

    boolean isMarked();


    boolean checkPassage( IBoat boat );

    boolean checkCollision( IBoat boat );

    Line getLine();
}
