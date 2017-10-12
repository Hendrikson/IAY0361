import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UnitTests {
    private static WeatherGateway weatherGateway;

    @BeforeClass
    public static void setUpWeatherObject() throws IOException{
        weatherGateway = WeatherGateway.getWeatherGatewayByCity();
        System.out.println(weatherGateway.getCurrentCityData());
    }

    @Test
    public void testCurrentTemperatureRange(){
        try {
            int minTemp = -273;
            int maxTemp = 500;
            double currentTemp = weatherGateway.getCurrentTemperature();
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
            for (int i = 0; i < weatherGateway.getForecastArrayLength(); i++) {
                double currentTemp = weatherGateway.getCurrentTemperatureFromArrayObject(weatherGateway.getForecastObjectFromArray(i));
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
            int currentHumidity = weatherGateway.getCurrentHumidity();
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
            for (int i = 0; i < weatherGateway.getForecastArrayLength(); i++) {
                int currentHumidity = weatherGateway.getCurrentHumidityFromArrayObject(weatherGateway.getForecastObjectFromArray(i));
                if (!(currentHumidity >= minRange && currentHumidity <= maxRange)) { fail("Array Index " + i + " Humidity out of range : " + currentHumidity); }
            }
        } catch (Exception e) {
            fail("Failure cause : " + e.getMessage());
        }
    }

    @Test
    public void testCountryCode() {
        try {
            String countryCode = weatherGateway.getCountryCode();
            assertTrue(countryCode.equals("EE"));
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
