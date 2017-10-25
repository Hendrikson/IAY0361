import file.FileReader;
import file.FileWriter;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import weather.CurrentWeather;
import weather.ForecastWeather;

import java.io.*;
import java.util.Random;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class UnitTests {
    private static CurrentWeather currentWeather;
    private static ForecastWeather forecastWeather;

    @BeforeClass
    public static void setUpWeatherObject() throws IOException{
        currentWeather = CurrentWeather.getCurrentWeather();
        forecastWeather = ForecastWeather.getForecastWeather();
    }

    @Before
    public void resetWeatherVariables() throws IOException{
        currentWeather.getNewCurrentWeather();
        forecastWeather.getNewForecastWeather();
    }

    @AfterClass
    public static void releaseObjects() {
        currentWeather = null;
        forecastWeather = null;
    }

    @Test
    public void checkObjects() throws IOException {
        assertTrue(currentWeather.equals(CurrentWeather.getCurrentWeather()));
    }

    @Test
    public void testCurrentTemperatureRange(){
        try {
            int minTemp = -273;
            int maxTemp = 500;
            double currentTemp = currentWeather.getCurrentTemperature();
            assertTrue("Temperature from current weather object must be in correct range.",
                    currentTemp >= minTemp && currentTemp <= maxTemp);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testForecastTemperatureRanges() {
        try {
            int minTemp = -273;
            int maxTemp = 500;
            for (int i = 0; i < forecastWeather.getForecastArrayLength(); i++) {
                double currentTemp = forecastWeather.getTemperatureFromArrayObject(i);
                if (!(currentTemp >= minTemp && currentTemp <= maxTemp)) { fail("Array Index " + i + "Temperature out of possible range : " + currentTemp); }
            }
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testHumidityRange() {
        try {
            int minRange = 0;
            int maxRange = 100;
            int currentHumidity = currentWeather.getCurrentHumidity();
            assertTrue("Humidity from current weather object must be in correct range." ,
                    currentHumidity >= minRange && currentHumidity <= maxRange);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherCountryCode() {
        try {
            String countryCode = currentWeather.getCountryCode();
            assertTrue("Country code must be EE.",
                    countryCode.equals("EE"));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testChosenCityEqualsCurrentWeather() {
        try {
            assertTrue("New object created with city name from current object must be equal to current object",
                    currentWeather.equals(CurrentWeather.getCurrentWeatherByCity(currentWeather.getCityName())));
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

            CurrentWeather currentWeatherTest = currentWeather;
            currentWeatherTest.getNewCurrentWeather(cityName);
            assertTrue("Randomly chosen city name will be set to test object and compared to new object created with the same city name.",
                    currentWeatherTest.equals(CurrentWeather.getCurrentWeatherByCity(cityName)));
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

            FileWriter fileWriter = new FileWriter();
            fileWriter.writeCityIntoInputFile(cityName);

            FileReader fileReader = new FileReader();

            assertTrue("Choose a name from cityNames and check whether it's written correctly to file.",
                    fileReader.readCityFromInput().equals(cityName));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testGetCityDataFromFileCurrentWeather() {
        try {
            // Choose a random city name from the list and write it into the input file.
            // Read it, creating a dummy object, and write the city data into the output file.
            // Read and compare the contents of the output file to the dummy object's output.
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            currentWeather.getNewCurrentWeather(cityName);

            FileWriter fileWriter = new FileWriter();
            fileWriter.writeCityIntoInputFile(cityName);
            fileWriter.writeDataIntoOutput(currentWeather.getCurrentCityData());

            FileReader fileReader = new FileReader();
            CurrentWeather currentWeather = CurrentWeather.getCurrentWeatherByCity(fileReader.readCityFromInput());

            String fileTextLines = fileReader.readLinesFromOutput();

            fileTextLines = fileTextLines.substring(0, fileTextLines.length()-1);
            System.out.println(currentWeather.getCurrentCityData());
            System.out.println(fileTextLines);
            assertTrue(currentWeather.getCurrentCityData().equals(fileTextLines));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCoordinatePairAsString(){
        try {
            String line = currentWeather.getCoordinatesAsString();
            Pattern p = Pattern.compile("(^0|^-0[^\\.])|(\\.$)");
            double x = Double.parseDouble(line.split(" ")[0].substring(1));
            double y = Double.parseDouble(line.split(" ")[1].substring(0, line.split(" ").length - 1));
            //System.out.println(x + " " + y);
            if(p.matcher(line.split(" ")[0]).find() || p.matcher(line.split(" ")[1]).find()) {
                fail();
            } else if(x >= -90 && x <= 90 && y >= -180 && y <= 180) {
                // If correct, return to pass the test.
                return;
            } else {
                fail();
            }
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherLatitudeValidity(){
        try {
            double latitude = currentWeather.getLatitudeAsDouble();
            assertTrue(latitude > -90 && latitude < 90);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherLongitudeValidity(){
        try {
            double longitude = currentWeather.getLongitudeAsDouble();
            assertTrue(longitude > -180 && longitude < 180);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }
}
