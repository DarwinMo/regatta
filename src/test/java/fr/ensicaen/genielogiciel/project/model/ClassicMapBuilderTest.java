package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClassicMapBuilderTest {

    @Test
    public void testSimpleMapBuild() {
        IWind wind = new ConcreteWind(new WeatherAPI());
        ConcreteCoast coast = new ConcreteCoast(1, 2, 3, 4);
        ConcreteBuoy buoy = new ConcreteBuoy(5, 6, 0);


        IMap map = new ClassicMapBuilder().addCoast(coast).addBuoy(buoy).build();

        assertNotNull(map);
        assertEquals(1, map.getCoasts().size());
        assertEquals(1, map.getBuoys().size());
    }
}
