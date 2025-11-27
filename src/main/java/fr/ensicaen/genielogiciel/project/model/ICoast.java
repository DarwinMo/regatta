package fr.ensicaen.genielogiciel.project.model;

public interface ICoast {
    double startX();

    double startY();

    double endX();

    double endY();

    boolean isCollision( IBoat boat );
}
