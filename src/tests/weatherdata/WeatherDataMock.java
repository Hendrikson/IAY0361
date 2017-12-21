package weatherdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import weather.WeatherCurrent;

import java.io.IOException;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherDataMock {
    @Test
    public void testWeatherDataCallsHttpUtilityMockFunction() {
        HttpUtility httpUtilityMock = mock(HttpUtility.class);
        WeatherData weatherDataSpy = spy(new WeatherData(httpUtilityMock));
        try {
            weatherDataSpy.getJsonData(""); // Always throws NullPointerException
        } catch (NullPointerException e) {
            try {
                verify(weatherDataSpy, times(1)).getJsonData("");
                verify(httpUtilityMock, times(1)).makeHttpGetRequest("");
            } catch (IOException io) {
                fail("Failed due to IOException");
            }
        } catch (Exception e) {
            fail("Exception caught: " + e.getMessage());
        }
    }

    @Test
    public void testCurrentWeatherCallsGetJsonDataFromWeatherDataMock() {
        WeatherData weatherDataMock = mock(WeatherData.class);
        try {
            WeatherCurrent weatherCurrent = new WeatherCurrent(weatherDataMock); // Always throws NullPointerException
        } catch (NullPointerException e) {
            try {
                verify(weatherDataMock, times(1)).getJsonData("http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71");
            } catch (IOException io) {
                fail("IOException caught!");
            }
        } catch(Exception e) {
            fail("Exception caught: " + e.getMessage());
        }
    }
}
