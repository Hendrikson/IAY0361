package weatherdata;

import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;

public class WeatherDataMain {
    @Test
    public void testWeatherDataReturnsJSON() {
        WeatherData weatherData = new WeatherData();
        try {
            JsonObject data = weatherData.getJsonData("http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71");
            assertTrue(data.getClass() == JsonObject.class);
        } catch (IOException e) {
            fail("IOException caught.");
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
