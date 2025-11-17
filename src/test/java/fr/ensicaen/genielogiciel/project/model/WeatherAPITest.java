package fr.ensicaen.genielogiciel.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeatherAPITest {

    @Test
    void getWindSpeed() {
        WeatherAPI weatherAPI = new WeatherAPI();
        double windSpeed = weatherAPI.getWindSpeed();
        assertTrue(windSpeed >= 0 && windSpeed <= 30, "Wind speed should be between 0 and 30");
    }

    @Test
    void getWindDirection() {
        WeatherAPI weatherAPI = new WeatherAPI();
        double direction = weatherAPI.getWindDirection();
        assertTrue(direction >= 0 && direction < 360, "Wind direction should be between 0 (inclusive) and 360 (exclusive)");
    }
}