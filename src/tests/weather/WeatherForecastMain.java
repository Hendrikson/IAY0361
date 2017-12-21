package weather;

import file.FileReader;
import file.FileWriter;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.Random;

import static org.junit.Assert.*;

public class WeatherForecastMain {
    private static WeatherForecast weatherForecast;

    @BeforeClass
    public static void setUpWeatherObject() throws IOException{
        weatherForecast = new WeatherForecast();
    }

    @Before
    public void resetWeatherVariables() throws IOException{
        weatherForecast.getNewWeatherData();
    }

    @AfterClass
    public static void releaseObjects() {
        weatherForecast = null;
    }

    @Test
    public void testWeatherForecastTemperatureRanges() {
        try {
            final int minTemp = -273;
            final int maxTemp = 500;
            for (int i = 0; i < weatherForecast.getForecastArrayLength(); i++) {
                double currentTemp = weatherForecast.getTemperatureFromArrayObject(i);
                if (!(currentTemp >= minTemp && currentTemp <= maxTemp)) {
                    fail("Array Index " + i + " Temperature out of possible range : " + currentTemp);
                }
            }
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testChosenCityEqualsWeatherForecast() {
        try {
            assertTrue(weatherForecast.equals(new WeatherForecast(weatherForecast.getCityName())));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testWritingCityIntoFileCurrentWeather() {
        try {
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testWritingCityToFileCurrentWeather : " + cityName);

            FileWriter fileWriter = new FileWriter(cityName);
            fileWriter.writeDataIntoInputFile(cityName);

            FileReader fileReader = new FileReader();

            assertTrue(fileReader.readCityFromInput().equals(cityName));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

}
