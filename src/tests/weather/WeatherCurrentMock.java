package weather;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import weatherutil.WeatherUtility;

import java.io.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherCurrentMock {
    private static WeatherCurrent weatherCurrentSpy;

    @BeforeClass
    public static void setUpWeatherObject() throws IOException{
        weatherCurrentSpy = spy(new WeatherCurrent());
    }

    @Before
    public void resetWeatherVariables() throws IOException {
        weatherCurrentSpy = spy(new WeatherCurrent());
    }

    @AfterClass
    public static void releaseObjects() {
        weatherCurrentSpy = null;
    }

    @Test
    public void testWeatherCurrentGetCurrentCityDataCallsRequiredFunctions() {
        WeatherUtility weatherUtility = new WeatherUtility();
        weatherUtility.getCityCurrentData(weatherCurrentSpy);

        verify(weatherCurrentSpy).getCityName();
        verify(weatherCurrentSpy).getCoordinatesAsString();
        verify(weatherCurrentSpy).getCurrentHumidity();
        verify(weatherCurrentSpy).getCurrentTemperature();
    }

    @Test
    public void testWeatherCurrentGetCoordinatesAsStringCallsRequiredFunctions() {
        weatherCurrentSpy.getCoordinatesAsString();
        verify(weatherCurrentSpy).getCoordinatesAsString();

        verify(weatherCurrentSpy).getLatitudeAsDouble();
        verify(weatherCurrentSpy).getLongitudeAsDouble();
    }

    @Test
    public void testWeatherCurrentEqualsCallsCorrectSubFunctions() {
        weatherCurrentSpy.equals(weatherCurrentSpy);
        verify(weatherCurrentSpy).equals(weatherCurrentSpy);

        verify(weatherCurrentSpy, times(2)).getCityName();
        verify(weatherCurrentSpy, times(2)).getCurrentTemperature();
        verify(weatherCurrentSpy, times(2)).getCurrentHumidity();
        verify(weatherCurrentSpy, times(2)).getCountryCode();
    }

}
