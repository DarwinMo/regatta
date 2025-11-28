package fr.ensicaen.genielogiciel.project.model;

import java.io.IOException;
import java.io.InputStream;

public class FigaroBoat extends AbstractBoat {

    private static final String POLAR = "/fr/ensicaen/genielogiciel/project/model/polaire-figaro.pol";

    public FigaroBoat( double x, double y, IDecoratorSail sail, IDecoratorEquipment crew ) {
        super(x, y, sail, crew);

        InputStream test = getClass().getResourceAsStream(POLAR);
        System.out.println("Test resource = " + (test == null ? "NOT FOUND" : "OK"));

        try {
            _polarTable = new PolarTable(POLAR);
        } catch (IOException e) {
            System.err.println("[WARNING] Impossible de charger la table polaire : " + e.getMessage());
            _polarTable = null;
        }
    }
}
