package fr.ensicaen.genielogiciel.project.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import fr.ensicaen.genielogiciel.project.model.FigaroFactory;
import fr.ensicaen.genielogiciel.project.model.IBoatFactory;
import fr.ensicaen.genielogiciel.project.model.Oceanis37Factory;

public enum BoatType {
    FIGARO, OCEANIS37;

    public static BoatType getBoatType( JsonNode boat ) {
        BoatType finalBoatType;
        String boatType = boat.path("boatType").asText("SINGLE");
        try {
            finalBoatType = BoatType.valueOf(boatType.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Unknown boat type: " + boatType + ". Defaulting to FIGARO.");
            finalBoatType = BoatType.FIGARO;
        }
        return finalBoatType;
    }


    public static IBoatFactory getBoatFactory( BoatType boatType, SailType sailType, CrewType crewType ) {
        if (boatType == BoatType.OCEANIS37) {
            return new Oceanis37Factory(SailType.getDecoratorSail(sailType), CrewType.getDecoratorEquipment(crewType));
        }
        return new FigaroFactory(SailType.getDecoratorSail(sailType), CrewType.getDecoratorEquipment(crewType));
    }
}
