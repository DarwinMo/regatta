package fr.ensicaen.genielogiciel.project.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PolarTable {
    private final double[][] _values;
    private final int[] _angles = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180};
    private final int[] _windSpeeds = {4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};

    public PolarTable( String fileName ) throws IOException {
        _values = new double[_angles.length][_windSpeeds.length];
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        int i = 0;
        while ((line = br.readLine()) != null && i < _angles.length) {
            String[] tokens = line.trim().split("\\s+");
            if (tokens.length != _windSpeeds.length) {
                br.close();
                throw new IOException("Nombre de colonnes incorrect dans le fichier .pol à la ligne" + (i + 1));
            }
            for (int j = 0; j < _windSpeeds.length; j++) {
                _values[i][j] = Double.parseDouble(tokens[j]);
            }
            i++;
        }
        br.close();
        if (i != _angles.length) {
            throw new IOException("Nombre de lignes incorrect dans le fichier .pol : attendu " + _angles.length + " lignes, trouvé " + i);
        }
    }

    public double getSpeed( double angle, double windSpeed ) {
        angle = Math.max(_angles[0], Math.min(_angles[_angles.length - 1], angle));
        windSpeed = Math.max(_windSpeeds[0], Math.min(_windSpeeds[_windSpeeds.length - 1], windSpeed));

        int i1 = 0;
        while (i1 < _angles.length - 1 && _angles[i1 + 1] < angle) i1++;
        int i2 = Math.min(i1 + 1, _angles.length - 1);

        double a1 = _angles[i1], a2 = _angles[i2];

        int j1 = 0;
        while (j1 < _windSpeeds.length - 1 && _windSpeeds[j1 + 1] < windSpeed) j1++;
        int j2 = Math.min(j1 + 1, _windSpeeds.length - 1);

        double s11 = _values[i1][j1];
        double s12 = _values[i1][j2];
        double s21 = _values[i2][j1];
        double s22 = _values[i2][j2];

        double fA = (a2 == a1) ? 0 : (angle - a1) / (a2 - a1);
        double fS = (_windSpeeds[j2] == _windSpeeds[j1]) ? 0 : (windSpeed - _windSpeeds[j1]) / (_windSpeeds[j2] - _windSpeeds[j1]);

        double s1 = s11 + fS * (s12 - s11);
        double s2 = s21 + fS * (s22 - s21);

        return s1 + fA * (s2 - s1);
    }
}