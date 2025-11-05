package fr.ensicaen.genielogiciel.project.model;

public interface ICoast {
    double getStartX();
    double getStartY();
    double getEndX();
    double getEndY();

    boolean isCollision(IBoat boat);
}
