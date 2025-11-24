package fr.ensicaen.genielogiciel.project.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import fr.ensicaen.genielogiciel.project.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonParser implements IParser {

    @Override
    public IMap loadGame( String filePath ) {
        ObjectMapper mapper = new ObjectMapper();
        IMapBuilder mapBuilder = new ClassicMapBuilder();
        try {
            JsonNode root = mapper.readTree(new File(filePath));

            JsonNode boat = root.get("boat");
            int boatX = boat.get("x").asInt();
            int boatY = boat.get("y").asInt();

            JsonNode waterPlan = root.get("waterPlan");
            int waterPlanWidth = waterPlan.get("width").asInt();
            int waterPlanHeight = waterPlan.get("height").asInt();
            mapBuilder.addWaterPlan(new ConcreteWaterPlan(waterPlanWidth, waterPlanHeight, new ConcreteWind(new WeatherAPI())));


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
                    int bx = buoy.get("x").asInt();
                    int by = buoy.get("y").asInt();
                    mapBuilder.addBuoy(new ConcreteBuoy(bx, by));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapBuilder.build();
    }
}
