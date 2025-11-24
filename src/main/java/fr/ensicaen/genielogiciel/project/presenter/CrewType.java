package fr.ensicaen.genielogiciel.project.presenter;

import com.fasterxml.jackson.databind.JsonNode;

public enum CrewType {
    SINGLE, DOUBLE;

    public static CrewType getCrewType( JsonNode boat ) {
        CrewType finalCrew;
        String crewType = boat.path("crewType").asText("SINGLE");
        try {
            finalCrew = CrewType.valueOf(crewType.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Unknown crew type: " + crewType + ". Defaulting to SINGLE.");
            finalCrew = CrewType.SINGLE;
        }
        return finalCrew;
    }
}
