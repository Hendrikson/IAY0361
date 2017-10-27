import file.FileReader;
import file.FileWriter;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import weather.WeatherCurrent;
import weather.WeatherForecast;

import java.io.*;
import java.util.Random;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class UnitTests {
    private static WeatherCurrent weatherCurrent;
    private static WeatherForecast weatherForecast;

    @BeforeClass
    public static void setUpWeatherObject() throws IOException{
        weatherCurrent = new WeatherCurrent();
        weatherForecast = new WeatherForecast();
    }

    @Before
    public void resetWeatherVariables() throws IOException{
        weatherCurrent.getNewWeatherData();
        weatherForecast.getNewWeatherData();
    }

    @AfterClass
    public static void releaseObjects() {
        weatherCurrent = null;
        weatherForecast = null;
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
            int minTemp = -273;
            int maxTemp = 500;
            double currentTemp = weatherCurrent.getCurrentTemperature();
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
            for (int i = 0; i < weatherForecast.getForecastArrayLength(); i++) {
                double currentTemp = weatherForecast.getTemperatureFromArrayObject(i);
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
            int currentHumidity = weatherCurrent.getCurrentHumidity();
            assertTrue("Humidity from current weather object must be in correct range." ,
                    currentHumidity >= minRange && currentHumidity <= maxRange);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherCountryCode() {
        try {
            String countryCode = weatherCurrent.getCountryCode();
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
                    weatherCurrent.equals(new WeatherCurrent(weatherCurrent.getCityName())));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testChosenCityEqualsWeatherForecast() {
        try {
            assertTrue("New object created with city name from current object must be equal to current object",
                    weatherForecast.equals(new WeatherForecast(weatherForecast.getCityName())));
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
            assertTrue("Randomly chosen city name will be set to test object and compared to new object created with the same city name.",
                    weatherCurrent.equals(new WeatherCurrent(cityName)));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testRandomCityEqualsWeatherForecast() {
        try {
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testRandomCityEqualsWeatherForecast : " + cityName);

            weatherForecast.getNewWeatherData(cityName);
            assertTrue("Randomly chosen city name will be set to test object and compared to new object created with the same city name.",
                    weatherForecast.equals(new WeatherForecast(cityName)));
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
    public void testGetCityDataFromFile() {
        try {
            // Choose a random city name from the list and write it into the input file.
            // Read it, creating a dummy object, and write the city data into the output file.
            // Read and compare the contents of the output file to the dummy object's output.
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testGetCityDataFromFile : " + cityName);

            UnitTests.weatherCurrent.getNewWeatherData(cityName);

            FileWriter fileWriter = new FileWriter();
            fileWriter.writeCityIntoInputFile(cityName);
            fileWriter.writeDataIntoOutput(UnitTests.weatherCurrent.getCurrentCityData());

            FileReader fileReader = new FileReader();
            WeatherCurrent weatherCurrent = new WeatherCurrent(fileReader.readCityFromInput());

            String fileTextLines = fileReader.readLinesFromOutput();

            fileTextLines = fileTextLines.substring(0, fileTextLines.length()-1);
            //System.out.println(weatherCurrent.getCurrentCityData());
            //System.out.println(fileTextLines);
            assertTrue(weatherCurrent.getCurrentCityData().equals(fileTextLines));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCoordinatePairAsString(){
        try {
            String line = weatherCurrent.getCoordinatesAsString();
            Pattern p = Pattern.compile("(^0|^-0[^.])|(\\.$)");
            double x = Double.parseDouble(line.split(" ")[0].replace("(", ""));
            double y = Double.parseDouble(line.split(" ")[1].replace(")", ""));
            //System.out.println(x + " " + y);
            if(p.matcher(line.split(" ")[0]).find() || p.matcher(line.split(" ")[1]).find()) {
                fail();
            } else if(x >= -90 && x <= 90 && y >= -180 && y <= 180) {
                // If correct, pass the test.
            } else {
                fail();
            }
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

            //System.out.println(cityNames[randomNum] + " " + weatherCurrent.getCoordinatesAsString() + " " + cityCoords[randomNum]);
            assertTrue(weatherCurrent.getCoordinatesAsString().equals(cityCoords[randomNum]));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }


    @Test
    public void testCurrentWeatherLatitudeValidity(){
        try {
            double latitude = weatherCurrent.getLatitudeAsDouble();
            assertTrue(latitude > -90 && latitude < 90);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherLongitudeValidity(){
        try {
            double longitude = weatherCurrent.getLongitudeAsDouble();
            assertTrue(longitude > -180 && longitude < 180);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }
}
