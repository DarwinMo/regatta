package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BoatDirectionManager {

    public static class BoatRecord {
        public double x;
        public double y;
        public double angle;
        public long time;

        public BoatRecord( double x, double y, double angle, long time ) {
            this.x = x;
            this.y = y;
            this.angle = angle;
            this.time = time;
        }
    }

    private final List<BoatRecord> _boatPositionsHistory;
    private final double _rotationAngle;
    private IBoat _boat = null;

    public BoatDirectionManager( double rotationAngle ) {
        _boatPositionsHistory = new ArrayList<>();
        _rotationAngle = rotationAngle;
    }

    public void setBoat( IBoat boat ) {
        _boat = boat;
    }

    public void goRight( long time ) {
        abstractChangeOrientationCommand command = new ConcreteGoRightCommand(_rotationAngle);
        execute(command, time);
    }

    public void goLeft( long time ) {
        abstractChangeOrientationCommand command = new ConcreteGoLeftCommand(_rotationAngle);
        execute(command, time);
    }

    public void goStraight( long timeline ) {
        abstractChangeOrientationCommand command = new ConcreteGoStraightCommand(timeline);
        execute(command, timeline);
    }

    private void execute( abstractChangeOrientationCommand command, long time ) {
        if (_boat != null) {
            command.execute(_boat);
        } else {
            return;
        }
        _boatPositionsHistory.add(new BoatRecord(_boat.get_x(), _boat.get_y(), _boat.get_angle(), time));
    }

    public void saveGame() {
        String filename = "./src/main/resources/fr/ensicaen/genielogiciel/project/model/game_cords.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            writer.write("X,Y,angle,time");
            writer.newLine();

            for (BoatRecord record : _boatPositionsHistory) {
                writer.write(record.x + "," + record.y + "," + record.angle + "," + record.time);
                writer.newLine();
            }
            System.out.println("Positions successfully saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public void loadGame() {
        String filename = "./src/main/resources/fr/ensicaen/genielogiciel/project/model/game_cords.csv";
        _boatPositionsHistory.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    try {
                        double x = Double.parseDouble(parts[0].trim());
                        double y = Double.parseDouble(parts[1].trim());
                        double angle = Double.parseDouble(parts[2].trim());
                        long time = Long.parseLong(parts[3].trim()); // Lecture du temps

                        _boatPositionsHistory.add(new BoatRecord(x, y, angle, time));

                    } catch (NumberFormatException nfe) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
            System.out.println("Game loaded! Positions restored: " + _boatPositionsHistory.size());

        } catch (IOException e) {
            System.err.println("Error while loading the game: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public List<BoatRecord> getBoatPositionsHistory() {
        return _boatPositionsHistory;
    }
}