package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;
import javafx.geometry.Point3D;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoatDirectionManager {
    private final List<Point3D> _boatPositionsHistory;
    private final double _rotationAngle;
    private IBoat _boat = null;

    public BoatDirectionManager( double rotationAngle ) {
        _boatPositionsHistory = new ArrayList<>();
        _rotationAngle = rotationAngle;
    }

    public void setBoat( IBoat boat ) {
        _boat = boat;
    }

    public void goRight() {
        abstractChangeOrientationCommand command = new ConcreteGoRightCommand(_rotationAngle);
        execute(command);
    }

    public void goLeft() {
        abstractChangeOrientationCommand command = new ConcreteGoLeftCommand(_rotationAngle);
        execute(command);
    }

    public void goStraight( long timeline ) {
        abstractChangeOrientationCommand command = new ConcreteGoStraightCommand(timeline);
        execute(command);
    }

    private void execute( abstractChangeOrientationCommand command ) {
        if (_boat != null) {
            command.execute(_boat);
        } else {
            return;
        }
        _boatPositionsHistory.add(new Point3D(_boat.get_x(), _boat.get_y(), _boat.get_angle()));
    }

    public void saveGame() {
        String filename = "./src/main/resources/fr/ensicaen/genielogiciel/project/model/game_cords.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            writer.write("X,Y,angle");
            writer.newLine();

            for (Point3D point : _boatPositionsHistory) {
                writer.write(point.getX() + "," + point.getY() + "," + point.getZ());
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
            reader.readLine();// Header skipped
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        double x = Double.parseDouble(parts[0].trim());
                        double y = Double.parseDouble(parts[1].trim());
                        double angle = Double.parseDouble(parts[2].trim());

                        _boatPositionsHistory.add(new Point3D(x, y, angle));

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


    public List<Point3D> getBoatPositionsHistory() {
        return _boatPositionsHistory;
    }
}
