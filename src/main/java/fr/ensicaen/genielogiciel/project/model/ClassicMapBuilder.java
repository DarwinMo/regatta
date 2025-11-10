package fr.ensicaen.genielogiciel.project.model;

import java.util.ArrayList;
import java.util.List;

public class ClassicMapBuilder implements IMapBuilder {

    private IWaterPlan waterPlan;
    private final List<IBuoy> buoys = new ArrayList<>();
    private final List<ICoast> coasts = new ArrayList<>();
    private IBoat boat;


    @Override
    public IMapBuilder addBuoy( IBuoy buoy ) {
        buoys.add(buoy);
        return this;
    }


    @Override
    public IMapBuilder addCoast( ICoast coast ) {
        coasts.add(coast);
        return this;
    }

    @Override
    public IMapBuilder addBoat( IBoat boat ) {
        this.boat = boat;
        return this;
    }

    @Override
    public IMapBuilder addWaterPlan( IWaterPlan waterPlan ) {
        this.waterPlan = waterPlan;
        return this;
    }

    @Override
    public IMap build() {
        return new IMap() {
            private IWaterPlan wp = waterPlan;
            private final List<ICoast> c = new ArrayList<>(coasts);
            private final List<IBuoy> b = new ArrayList<>(buoys);
            private IBoat bt = boat;

            @Override
            public IWaterPlan getWaterPlan() {
                return wp;
            }

            @Override
            public List<ICoast> getCoasts() {
                return c;
            }

            @Override
            public List<IBuoy> getBuoys() {
                return b;
            }

            @Override
            public IBoat getBoat() {
                return bt;
            }

            @Override
            public void setWaterPlan( IWaterPlan plan ) {
                wp = plan;
            }

            @Override
            public void addCoast( ICoast coast ) {
                c.add(coast);
            }

            @Override
            public void addBuoy( IBuoy buoy ) {
                b.add(buoy);
            }

            @Override
            public void setBoat( IBoat boat ) {
                bt = boat;
            }
        };
    }
}
