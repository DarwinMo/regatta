package fr.ensicaen.genielogiciel.project.model;

import java.util.List;

public interface IMap {
    IWaterPlan getWaterPlan();

    List<ICoast> getCoasts();

    List<IBuoy> getBuoys();

    IBoat getBoat();

    void setWaterPlan( IWaterPlan waterPlan );

    void addCoast( ICoast coast );

    void addBuoy( IBuoy buoy );

    void setBoat( IBoat boat );


}
