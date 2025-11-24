package fr.ensicaen.genielogiciel.project.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import fr.ensicaen.genielogiciel.project.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonParser implements IParser {
    private IDecoratorSail _getDecoratorSail( SailType sailType ) {
        if (sailType == SailType.MEDIUM) {
            return new MediumSail();
        }
        return new LargeSail();
    }

    private IDecoratorEquipment _getDecoratorEquipment( CrewType crewType ) {
        if (crewType == CrewType.SINGLE) {
            return new SingleCrew();
        }
        return new DoubleCrew();
    }

    private IBoatFactory _getBoatFactory( BoatType boatType, SailType sailType, CrewType crewType ) {
        if (boatType == BoatType.OCEANIS37) {
            return new Oceanis37Factory(_getDecoratorSail(sailType), _getDecoratorEquipment(crewType));
        }
        return new FigaroFactory(_getDecoratorSail(sailType), _getDecoratorEquipment(crewType));
    }

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

            mapBuilder.addBoat(_getBoatFactory(finalBoatType, finalsailType, finalCrewType).createBoat(boatX, boatY));

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

            return mapBuilder.build();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
