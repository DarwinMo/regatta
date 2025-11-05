package fr.ensicaen.genielogiciel.project.model;

public interface IMapBuilder {
    IMap build();
    IMapBuilder addWaterPlan(IWaterPlan waterPlan);
    IMapBuilder addCoast(ICoast coast);
    IMapBuilder addBuoy(IBuoy buoy);
    IMapBuilder addBoat(IBoat boat);
}
