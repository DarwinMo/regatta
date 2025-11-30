package fr.ensicaen.genielogiciel.project.model;

import java.io.IOException;
import java.io.InputStream;

public class Oceanis37Boat extends AbstractBoat {

    private static final String POLAR = "/fr/ensicaen/genielogiciel/project/model/polaire-oceanis-37.pol";

    public Oceanis37Boat( double x, double y, IDecoratorSail sail, IDecoratorEquipment crew ) {
        super(x, y, sail, crew);
        try (InputStream test = getClass().getResourceAsStream(POLAR)) {
            System.out.println("Test resource = " + (test == null ? "NOT FOUND" : "OK"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            _polarTable = new PolarTable(POLAR);
        } catch (IOException e) {
            System.err.println("[WARNING] Impossible de charger la table polaire : " + e.getMessage());
            _polarTable = null;
        }
    }


}
