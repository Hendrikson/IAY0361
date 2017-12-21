package weatherdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import weather.WeatherCurrent;
import weather.WeatherForecast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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
        String requestString = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        HttpUtility httpUtilityMock = mock(HttpUtility.class);
        WeatherData weatherDataSpy = spy(new WeatherData(httpUtilityMock));
        try {
            when(httpUtilityMock.makeHttpGetRequest(requestString)).thenReturn((HttpURLConnection) new URL(requestString).openConnection());
            WeatherCurrent weatherCurrent = new WeatherCurrent(weatherDataSpy, "Tallinn");

            verify(weatherDataSpy, times(1)).getJsonData(requestString);
            verify(httpUtilityMock, times(1)).makeHttpGetRequest(requestString);
        } catch(Exception e) {
            fail("Exception caught: " + e.getMessage());
        }
    }

    @Test
    public void testForecastWeatherCallsGetJsonDataFromWeatherDataMock() {
        String requestString = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";
        HttpUtility httpUtilityMock = mock(HttpUtility.class);
        WeatherData weatherDataSpy = spy(new WeatherData(httpUtilityMock));
        try {
            when(httpUtilityMock.makeHttpGetRequest(requestString)).thenReturn((HttpURLConnection) new URL(requestString).openConnection());
            WeatherForecast weatherForecast = new WeatherForecast(weatherDataSpy, "Tallinn");

            verify(weatherDataSpy, times(1)).getJsonData(requestString);
            verify(httpUtilityMock, times(1)).makeHttpGetRequest(requestString);
        } catch (Exception e) {
            fail("Exception caught: " + e.getMessage());
        }
    }
}
