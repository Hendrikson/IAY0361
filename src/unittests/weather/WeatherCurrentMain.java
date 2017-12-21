package weather;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.Random;

import static org.junit.Assert.*;

public class WeatherCurrentMain {
    private static WeatherCurrent weatherCurrent;

    @BeforeClass
    public static void setUpWeatherObject() throws IOException{
        weatherCurrent = new WeatherCurrent();
    }

    @Before
    public void resetWeatherVariables() throws IOException{
        weatherCurrent.getNewWeatherData();
    }

    @AfterClass
    public static void releaseObjects() {
        weatherCurrent = null;
    }

    @Test
    public void testCurrentWeatherEquals() throws IOException {
        assertTrue(weatherCurrent.equals(new WeatherCurrent()));
    }

    @Test
    public void testWeatherForecastEquals() throws IOException {
        assertTrue(weatherCurrent.equals(new WeatherCurrent()));
    }

    @Test
    public void testCurrentTemperatureRange(){
        try {
            final int minTemp = -273;
            final int maxTemp = 500;
            double currentTemp = weatherCurrent.getCurrentTemperature();
            assertTrue(currentTemp >= minTemp && currentTemp <= maxTemp);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testHumidityRange() {
        try {
            final int minRange = 0;
            final int maxRange = 100;
            int currentHumidity = weatherCurrent.getCurrentHumidity();
            assertTrue(currentHumidity >= minRange && currentHumidity <= maxRange);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherCountryCode() {
        try {
            String countryCode = weatherCurrent.getCountryCode();
            assertTrue(countryCode.equals("EE"));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testChosenCityEqualsCurrentWeather() {
        try {
            assertTrue(weatherCurrent.equals(new WeatherCurrent(weatherCurrent.getCityName())));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testRandomCityEqualsCurrentWeather() {
        try {
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testRandomCityEqualsCurrentWeather : " + cityName);

            weatherCurrent.getNewWeatherData(cityName);
            assertTrue(weatherCurrent.equals(new WeatherCurrent(cityName)));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCoordinatePairAsString(){
        try {
            String line = weatherCurrent.getCoordinatesAsString();
            if (line.split(" ").length != 2) {
                fail("Incorrect input pattern.");
            }
            double latitude = Double.parseDouble(line.split(" ")[0].replace("(", ""));
            double longitude = Double.parseDouble(line.split(" ")[1].replace(")", ""));
            final double minLatitude = -90;
            final double maxLatitude = 90;
            final double minLongitude = -180;
            final double maxLongitude = 180;
            assertTrue("Incorrect input range.",
                    latitude >= minLatitude && latitude <= maxLatitude
                            && longitude >= minLongitude && longitude <= maxLongitude);
        } catch (NumberFormatException e) {
            fail("Inputs are not convertible to type double.");
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testRandomCityCoordinateStringValidity() {
        try {
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            String[] cityCoords = new String[]{"(24.75 59.44)", "(24.5 58.39)", "(26.73 58.38)"
                    , "(27.02 57.83)", "(26.36 59.35)"};
            Random random = new Random();
            int randomNum = random.nextInt(cityNames.length);
            System.out.println("testRandomCityCoordinateStringValidity : " + cityNames[randomNum]);

            weatherCurrent.getNewWeatherData(cityNames[randomNum]);

            assertTrue("Coordinates do not match",
                    weatherCurrent.getCoordinatesAsString().equals(cityCoords[randomNum]));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherLatitudeValidity(){
        try {
            final double minLatitude = -90;
            final double maxLatitude = 90;
            double latitude = weatherCurrent.getLatitudeAsDouble();
            assertTrue(latitude > minLatitude && latitude < maxLatitude);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherLongitudeValidity(){
        try {
            final double minLongitude = -180;
            final double maxLongitude = 180;
            double longitude = weatherCurrent.getLongitudeAsDouble();
            assertTrue(longitude > minLongitude && longitude < maxLongitude);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }
}
