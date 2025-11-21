package fr.ensicaen.genielogiciel.project.model;

public interface IBuoy {
    double getX();

    // to implement : it tells if palyer went throughout the buoy
    boolean isMarked();

    double getY();
}
