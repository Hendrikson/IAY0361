package weather;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastMock {
    private static WeatherForecast weatherForecastSpy;

    @BeforeClass
    public static void setUpWeatherObject() throws IOException{
        weatherForecastSpy = spy(new WeatherForecast());
    }

    @Before
    public void resetWeatherVariables() throws IOException {
        weatherForecastSpy = spy(new WeatherForecast());
    }

    @AfterClass
    public static void releaseObjects() {
        weatherForecastSpy = null;
    }

    @Test
    public void testWeatherForecastEqualsCallsCorrectSubFunctions() {
        weatherForecastSpy.equals(weatherForecastSpy);
        verify(weatherForecastSpy).equals(weatherForecastSpy);

        verify(weatherForecastSpy, times(weatherForecastSpy.getForecastArrayLength() * 5 + 4)).getForecastArrayLength();
        for(int i = 0; i < weatherForecastSpy.getForecastArrayLength(); i++) {
            verify(weatherForecastSpy, times(6)).getTemperatureFromArrayObject(i);
        }
        verify(weatherForecastSpy, times(2)).getCityName();
        verify(weatherForecastSpy, times(2)).getHighestTemperatureFromArray();
        verify(weatherForecastSpy, times(2)).getLowestTemperatureFromArray();
    }

    @Test
    public void testWeatherForecastArrayMethodsCallCorrectAmountOfSubFunctions() {
        weatherForecastSpy.getHighestTemperatureFromArray();
        verify(weatherForecastSpy).getHighestTemperatureFromArray();

        weatherForecastSpy.getLowestTemperatureFromArray();
        verify(weatherForecastSpy).getLowestTemperatureFromArray();

        verify(weatherForecastSpy, times(weatherForecastSpy.getForecastArrayLength() * 2 + 1)).getForecastArrayLength();
        verify(weatherForecastSpy, times(2)).getTemperatureFromArrayObject(0);
        for (int i = 0; i < weatherForecastSpy.getForecastArrayLength(); i++) {
            verify(weatherForecastSpy, times(2)).getTemperatureFromArrayObject(i);
        }
    }

}
