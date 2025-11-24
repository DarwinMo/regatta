package fr.ensicaen.genielogiciel.project.presenter;

import com.fasterxml.jackson.databind.JsonNode;

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
}
