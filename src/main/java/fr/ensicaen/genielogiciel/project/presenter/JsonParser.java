package fr.ensicaen.genielogiciel.project.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import fr.ensicaen.genielogiciel.project.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonParser implements IParser {

    @Override
    public IMap loadGame( String filePath ) {
        if (filePath == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        IMapBuilder mapBuilder;
        try {
            mapBuilder = new ClassicMapBuilder();

            JsonNode root = mapper.readTree(new File(filePath));

            JsonNode boat = root.get("boat");
            int boatX = boat.get("x").asInt();
            int boatY = boat.get("y").asInt();

            SailType finalsailType = SailType.getSailType(boat);
            CrewType finalCrewType = CrewType.getCrewType(boat);
            BoatType finalBoatType = BoatType.getBoatType(boat);

            IWind weather = new ConcreteWind(new WeatherAPI());

            mapBuilder.addBoat(BoatType.getBoatFactory(finalBoatType, finalsailType, finalCrewType).createBoat(boatX, boatY, weather.getWindSpeed(), weather.getWindDirection()));

            JsonNode coastsArray = root.get("coasts");
            if (coastsArray.isArray()) {
                for (JsonNode coast : coastsArray) {
                    int sx = coast.get("s_x").asInt();
                    int sy = coast.get("s_y").asInt();
                    int ex = coast.get("e_x").asInt();
                    int ey = coast.get("e_y").asInt();
                    mapBuilder.addCoast(new ConcreteCoast(sx, sy, ex, ey));
                }
            }

            JsonNode buoysArray = root.get("buoys");
            if (buoysArray.isArray()) {
                for (JsonNode buoy : buoysArray) {
                    double bx = buoy.get("x").asDouble();
                    double by = buoy.get("y").asDouble();
                    double angle = buoy.get("angle").asDouble();
                    mapBuilder.addBuoy(new ConcreteBuoy(bx, by, angle));
                }
            }

            mapBuilder.addWind(new ConcreteWind(new WeatherAPI()));

            return mapBuilder.build();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
            return null;
        }
    }
}