package fr.ensicaen.genielogiciel.project.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PolarTable {
    private final double[][] _values;
    private final int[] _angles = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180};
    private final int[] _windSpeeds = {4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};

    public PolarTable( String resourcePath ) throws IOException {
        _values = new double[_angles.length][_windSpeeds.length];

        InputStream input = getClass().getResourceAsStream(resourcePath);
        if (input == null) {
            throw new IOException("Fichier polaire introuvable dans le classpath : " + resourcePath);
        }
        InputStream is = getClass().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IOException("Fichier " + resourcePath + " introuvable dans le classpath !");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        int i = 0;
        while ((line = br.readLine()) != null && i < _angles.length) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue; // ignore commentaires

            String[] tokens = line.split("\\s+");

            if (tokens[0].equals("4")) continue;

            if (tokens.length != _windSpeeds.length + 1) {
                throw new IOException("Nombre de colonnes incorrect dans le fichier .pol à la ligne " + (i + 1));
            }

            for (int j = 0; j < _windSpeeds.length; j++) {
                _values[i][j] = Double.parseDouble(tokens[j + 1]);
            }
            i++;
        }

        if (i != _angles.length) {
            throw new IOException("Nombre de lignes incorrect dans le fichier .pol : attendu " + _angles.length + ", trouvé " + i);

        }
    }

    public double getSpeed( double boatAngle, double windSpeed ) {
        boatAngle = boatAngle % 360;

        if (boatAngle > 180) {
            boatAngle = 360 - boatAngle;
        }

        windSpeed = Math.max(_windSpeeds[0], Math.min(_windSpeeds[_windSpeeds.length - 1], windSpeed));

        int lowerAngleIndex = 0;
        while (lowerAngleIndex < _angles.length - 1 && _angles[lowerAngleIndex + 1] < boatAngle) {
            lowerAngleIndex++;
        }
        int upperAngleIndex = Math.min(lowerAngleIndex + 1, _angles.length - 1);
        double lowerAngle = _angles[lowerAngleIndex];
        double upperAngle = _angles[upperAngleIndex];

        int lowerWindIndex = 0;
        while (lowerWindIndex < _windSpeeds.length - 1 && _windSpeeds[lowerWindIndex + 1] < windSpeed) {
            lowerWindIndex++;
        }
        int upperWindIndex = Math.min(lowerWindIndex + 1, _windSpeeds.length - 1);
        double lowerWind = _windSpeeds[lowerWindIndex];
        double upperWind = _windSpeeds[upperWindIndex];

        double speedLL = _values[lowerAngleIndex][lowerWindIndex];
        double speedLU = _values[lowerAngleIndex][upperWindIndex];
        double speedUL = _values[upperAngleIndex][lowerWindIndex];
        double speedUU = _values[upperAngleIndex][upperWindIndex];

        double angleFactor = (upperAngle == lowerAngle) ? 0 : (boatAngle - lowerAngle) / (upperAngle - lowerAngle);
        double windFactor = (upperWind == lowerWind) ? 0 : (windSpeed - lowerWind) / (upperWind - lowerWind);

        double speedLower = speedLL + windFactor * (speedLU - speedLL);
        double speedUpper = speedUL + windFactor * (speedUU - speedUL);

        return speedLower + angleFactor * (speedUpper - speedLower);
    }

}
