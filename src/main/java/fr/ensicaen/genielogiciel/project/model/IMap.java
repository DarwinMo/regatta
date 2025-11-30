package fr.ensicaen.genielogiciel.project.model;

import java.util.List;

public interface IMap {

    List<ICoast> getCoasts();

    List<IBuoy> getBuoys();

    IBoat getBoat();

    void setBoat( IBoat boat );

    IWind getWind();


}
