package fr.ensicaen.genielogiciel.project.model;

import java.util.ArrayList;
import java.util.List;

public class ClassicMapBuilder implements IMapBuilder {

    private final List<IBuoy> buoys = new ArrayList<>();
    private final List<ICoast> coasts = new ArrayList<>();
    private IBoat boat;
    private IWind _wind;


    public IMapBuilder addWind( IWind wind ) {
        _wind = wind;
        return this;
    }

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
    public void addBoat( IBoat boat ) {
        this.boat = boat;
    }


    @Override
    public IMap build() {
        return new IMap() {
            private final List<ICoast> c = new ArrayList<>(coasts);
            private final List<IBuoy> b = new ArrayList<>(buoys);
            private IBoat bt = boat;
            private final IWind w = _wind;


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
            public void setBoat( IBoat boat ) {
                bt = boat;
            }

            @Override
            public IWind getWind() {
                return w;
            }
        };
    }
}
