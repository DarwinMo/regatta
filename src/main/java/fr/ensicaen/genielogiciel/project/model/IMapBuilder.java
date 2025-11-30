package fr.ensicaen.genielogiciel.project.model;

public interface IMapBuilder {
    IMap build();

    IMapBuilder addCoast( ICoast coast );

    IMapBuilder addBuoy( IBuoy buoy );

    IMapBuilder addWind( IWind wind );

    void addBoat( IBoat boat );
}
