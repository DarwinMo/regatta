package fr.ensicaen.genielogiciel.project.presenter;

import fr.ensicaen.genielogiciel.project.model.IBoat;
import javafx.geometry.Point3D;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BoatDirectionManager {
    private final List<AbstractChangeOrientationCommande> _history;
    private final List<Point3D> _boatPositionsHistory;
    private final double _rotationAngle;
    private IBoat _boat = null;

    public BoatDirectionManager( double rotationAngle ) {
        _history = new ArrayList<>();
        _boatPositionsHistory = new ArrayList<>();
        _rotationAngle = rotationAngle;
    }

    public void setBoat( IBoat boat ) {
        _boat = boat;
    }

    public void goRight( long timeline ) {
        AbstractChangeOrientationCommande commande = new ConcreteGoRightCommande(_rotationAngle, timeline);
        execute(commande);
    }

    public void goLeft( long timeline ) {
        AbstractChangeOrientationCommande commande = new ConcreteGoLeftCommande(_rotationAngle, timeline);
        execute(commande);
    }

    public void goStraight( long timeline ) {
        AbstractChangeOrientationCommande commande = new ConcreteGoStraightCommande(timeline);
        execute(commande);
    }

    private void execute( AbstractChangeOrientationCommande commande ) {
        if (_boat != null) {
            commande.execute(_boat);
        } else {
            return;
        }
        _history.add(commande);
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
            System.err.println("Error while saving the game positions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadGame() {
        String filename = "./src/main/resources/fr/ensicaen/genielogiciel/project/model/game_cords.csv";

        _boatPositionsHistory.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;
            if ((line = reader.readLine()) != null) {
                // Header skipped
            }
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
            e.printStackTrace();
        }
    }


    public List<AbstractChangeOrientationCommande> getHistory() {
        return _history;
    }

    public List<Point3D> getBoatPositionsHistory() {
        return _boatPositionsHistory;
    }
}
