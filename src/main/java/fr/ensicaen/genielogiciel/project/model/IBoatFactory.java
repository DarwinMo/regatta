package fr.ensicaen.genielogiciel.project.model;

import java.io.IOException;

public interface IBoatFactory {
    IBoat createBoat( double x, double y ) throws IOException;
}
