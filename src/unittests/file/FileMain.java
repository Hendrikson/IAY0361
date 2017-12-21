package file;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import weather.WeatherCurrent;
import weatherutil.WeatherUtility;

import java.io.*;
import java.util.Random;

import static org.junit.Assert.*;

public class FileMain {
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

    @Test
    public void testGetCityNameFromFile() {
        try {
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testGetCityNameFromFile : " + cityName);

            FileWriter fileWriter = new FileWriter(cityName);
            fileWriter.writeDataIntoInputFile(cityName);

            weatherCurrent.getNewWeatherDataFromFile();

            assertTrue(weatherCurrent.getCityName().equals(cityName));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testGetCityDataFromFile() {
        try {
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testGetCityDataFromFile : " + cityName);

            FileWriter fileWriter = new FileWriter(cityName);
            fileWriter.writeDataIntoInputFile(cityName);

            WeatherUtility weatherUtility = new WeatherUtility();

            weatherCurrent.getNewWeatherDataFromFile();
            fileWriter.writeDataIntoOutputFile(weatherUtility.getCityCurrentData(weatherCurrent));

            FileReader fileReader = new FileReader();
            WeatherCurrent weatherCurrent = new WeatherCurrent(fileReader.readCityFromInput());

            String fileTextLines = fileReader.readLinesFromOutput(cityName);
            fileTextLines = fileTextLines.substring(0, fileTextLines.length()-1);
            assertTrue(weatherUtility.getCityCurrentData(weatherCurrent).equals(fileTextLines));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }
}
