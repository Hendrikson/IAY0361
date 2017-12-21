package weatherdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
            weatherDataSpy.getJsonData("");
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        try {
            verify(weatherDataSpy, times(1)).getJsonData("");
            verify(httpUtilityMock, times(1)).makeHttpGetRequest("");
        } catch (IOException e) {
            fail("Failed due to IOException");
        }
    }
}
