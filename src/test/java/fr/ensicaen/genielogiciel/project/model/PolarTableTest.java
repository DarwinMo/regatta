package fr.ensicaen.genielogiciel.project.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class PolarTableTest {

    @Test
    void testGetSpeed() throws IOException {
        PolarTable table = new PolarTable("/fr/ensicaen/genielogiciel/project/model/polaire-figaro.pol");

        double speedNull = table.getSpeed(20, 30);
        assertEquals(0, speedNull);

        double speed = table.getSpeed(30, 10);
        assertTrue(speed > 0);

        double speedSym = table.getSpeed(200, 10);
        assertEquals(table.getSpeed(160, 10), speedSym);

        assertEquals(table.getSpeed(30, 4), table.getSpeed(30, 0));

        assertEquals(table.getSpeed(30, 30), table.getSpeed(30, 35));
    }
}