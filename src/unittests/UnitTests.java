import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import weather.CurrentWeather;
import weather.ForecastWeather;

import java.io.*;
import java.util.Random;

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
        // System.out.println(weatherGateway.getCurrentCityData());
    }

    @Test
    public void testCurrentTemperatureRange(){
        try {
            int minTemp = -273;
            int maxTemp = 500;
            double currentTemp = currentWeather.getCurrentTemperature();
            assertTrue(currentTemp >= minTemp && currentTemp <= maxTemp);
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
            assertTrue(currentHumidity >= minRange && currentHumidity <= maxRange);
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testForecastHumidityRanges() {
        try {
            int minRange = 0;
            int maxRange = 100;
            for (int i = 0; i < forecastWeather.getForecastArrayLength(); i++) {
                int currentHumidity = forecastWeather.getHumidityFromArrayObject(i);
                if (!(currentHumidity >= minRange && currentHumidity <= maxRange)) { fail("Array Index " + i + " Humidity out of range : " + currentHumidity); }
            }
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherCountryCode() {
        try {
            String countryCode = currentWeather.getCountryCode();
            assertTrue(countryCode.equals("EE"));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testForecastWeatherCountryCode() {
        try {
            String countryCode = forecastWeather.getCountryCode();
            assertTrue(countryCode.equals("EE"));
        } catch (Exception e) {
            fail("failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testChosenCityEqualsCurrentWeather() {
        try {
            assertTrue(currentWeather.equals(CurrentWeather.getCurrentWeatherByCity(currentWeather.getCityName())));
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
            assertTrue(currentWeatherTest.equals(CurrentWeather.getCurrentWeatherByCity(cityName)));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testChosenCityEqualsForecastWeather() {
        try {
            assertTrue(forecastWeather.equals(ForecastWeather.getForecastWeatherByCity(forecastWeather.getCityName())));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testRandomCityEqualsForecastWeather() {
        try {
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testRandomCityEqualsForecastWeather : " + cityName);

            ForecastWeather forecastWeatherTest = forecastWeather;
            forecastWeatherTest.getNewForecastWeather(cityName);
            assertTrue(forecastWeatherTest.equals(ForecastWeather.getForecastWeatherByCity(cityName)));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testWritingCityIntoFile() {
        try {
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testWritingCityToFile : " + cityName);
            CurrentWeather.writeCityToFile(cityName);
            System.out.println(CurrentWeather.readCityFromFile());
            assertTrue(CurrentWeather.readCityFromFile().equals(cityName));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testGetCityDataFromFile() {
        try {
            // Choose a random city name from the list and write it into the input file.
            // Generate the correct response from .getCurrentCityData and check whether
            // Writing into file was successful or not.
            String[] cityNames = new String[]{"Tallinn", "Parnu", "Tartu", "Voru", "Rakvere"};
            Random random = new Random();
            String cityName = cityNames[random.nextInt(cityNames.length)];
            System.out.println("testGetCityDataFromFile : " + cityName);

            String outputFileName = "C:\\Users\\Karl\\IdeaProjects\\Automaattestimine\\IAY0361\\src\\src\\output.txt";
            CurrentWeather.writeCityToFile(cityName);

            WeatherGateway.getWeatherGatewayByCityFromFile(cityName);
            CurrentWeather currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
            BufferedReader br;
            FileReader fr;
            fr = new FileReader(outputFileName);
            br = new BufferedReader(fr);
            String fileTextLines = "";

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                fileTextLines += currentLine + "\n";
            }
            fileTextLines = fileTextLines.substring(0, fileTextLines.length()-1);
            assertTrue(currentWeather.getCurrentCityData().equals(fileTextLines));
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    //@Test
    //public void testCoordinatePairAsString(){
    //    try {
    //        String line = Placeholder.getCoordinateString();
    //        Pattern p = Pattern.compile("(^0|^-0[^\\.])|(\\.$)");
    //        double x = Double.parseDouble(line.split(" ")[0]);
    //        double y = Double.parseDouble(line.split(" ")[1]);
    //        //System.out.println(x + " " + y);
    //        if(p.matcher(line.split(" ")[0]).find() || p.matcher(line.split(" ")[1]).find()) {
    //            fail();
    //        } else if(x >= -90 && x <= 90 && y >= -180 && y <= 180) {
    //            return;
    //        } else {
    //            fail();
    //        }
    //    } catch (Exception e) {
    //        fail("Failure cause : " + e.getMessage());
    //    }
    //}

    //@Test
    //public void testCoordinateArraySize(){
    //    try {
    //        assertTrue(Placeholder.getCoordinates().size() == 2);
    //    } catch (Exception e) {
    //        fail("Failure cause : " + e.getMessage());
    //    }
    //}

    //@Test
    //public void testLatitudeValidity(){
    //    try {
    //        double latitude = currentWeatherData.get("coord").getAsJsonObject().get("lat").getAsDouble();
    //        assertTrue(latitude > -90 && latitude < 90);
    //    } catch (Exception e) {
    //        fail("Failure cause : " + e.getMessage());
    //    }
    //}

    //@Test
    //public void testLongitudeValidity(){
    //    try {
    //        double longitude = currentWeatherData.get("coord").getAsJsonObject().get("lon").getAsDouble();
    //        assertTrue(longitude > -180 && longitude < 180);
    //    } catch (Exception e) {
    //        fail("Failure cause : " + e.getMessage());
    //    }
    //}
}
