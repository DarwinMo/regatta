package fr.ensicaen.genielogiciel.project.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import fr.ensicaen.genielogiciel.project.model.IDecoratorSail;
import fr.ensicaen.genielogiciel.project.model.LargeSail;
import fr.ensicaen.genielogiciel.project.model.MediumSail;

public enum SailType {
    MEDIUM, LARGE;

    public static SailType getSailType( JsonNode boat ) {
        SailType finalSail;
        String sailType = boat.get("sailType").asText("MEDIUM");
        try {
            finalSail = SailType.valueOf(sailType.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Unknown sail type: '" + sailType + "'. Defaulting to MEDIUM.");
            finalSail = SailType.MEDIUM;
        }
        return finalSail;
    }

    public static IDecoratorSail getDecoratorSail( SailType sailType ) {
        if (sailType == SailType.MEDIUM) {
            return new MediumSail();
        }
        return new LargeSail();
    }
}
