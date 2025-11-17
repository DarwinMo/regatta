package fr.ensicaen.genielogiciel.project.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrevisionMeteoAPI {
    private static final String API_URL = "https://www.prevision-meteo.ch/services/json/lat=49.283lng=-0.25";

    private final Random _random = new Random();
    private double _windSpeed;
    private double _windDirection;
    private boolean _dataValid = false;

    public PrevisionMeteoAPI() {
        try {
            fetchWeatherData();
            System.out.println("Utilisation vent API");
        } catch (Exception e) {
            System.err.println("Erreur connexion API météo : " + e.getMessage());
            System.out.println("Utilisation vent random");
            setRandomValues();
        }
    }

    private void fetchWeatherData() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Réponse HTTP non valide : " + responseCode);
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            parseWeatherData(response.toString());
        }
    }

    private void parseWeatherData( String json ) {
        try {
            Pattern speedPattern = Pattern.compile("\"wnd_spd\"\\s*:\\s*([0-9.]+)");
            Pattern dirPattern = Pattern.compile("\"wnd_dir\"\\s*:\\s*\"([A-Z]+)\"");

            Matcher speedMatcher = speedPattern.matcher(json);
            Matcher dirMatcher = dirPattern.matcher(json);

            if (speedMatcher.find() && dirMatcher.find()) {
                _windSpeed = Double.parseDouble(speedMatcher.group(1));

                String dirCardinal = dirMatcher.group(1);
                _windDirection = cardinalToDegrees(dirCardinal);

                _dataValid = true;
            } else {
                throw new IllegalArgumentException("Champs vent non trouvés dans current_condition");
            }

        } catch (Exception e) {
            System.err.println("Données invalides ou incomplètes : " + e.getMessage());
            setRandomValues();
        }
    }

    private double cardinalToDegrees( String dir ) {
        return switch (dir.toUpperCase()) {
            case "N" -> 0;
            case "NE" -> 45;
            case "E" -> 90;
            case "SE" -> 135;
            case "S" -> 180;
            case "SW" -> 225;
            case "W" -> 270;
            case "NW" -> 315;
            default -> _random.nextDouble() * 360;
        };
    }

    private void setRandomValues() {
        _windSpeed = 1 + _random.nextDouble() * 20;
        _windDirection = _random.nextDouble() * 360;
        _dataValid = false;
    }

    public double getWindSpeed() {
        return _windSpeed;
    }

    public double getWindDirection() {
        return _windDirection;
    }

}
