package weatherhelper;

import file.FileReader;
import file.FileWriter;
import weather.WeatherCurrent;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherHelper {
    public void readAndWriteInputCitiesData() throws IOException{
        FileReader fileReader = new FileReader();
        ArrayList<String> cityNames = fileReader.readCitiesFromInput();
        if (cityNames.size() == 0) {
            System.out.println("Empty ArrayList");
            return;
        }

        FileWriter fileWriter = new FileWriter(cityNames.get(0));
        WeatherCurrent weatherCurrent = new WeatherCurrent(cityNames.get(0));

        for (String cityName : cityNames) {
            fileWriter.setCity(cityName);
            weatherCurrent.getNewWeatherData(cityName);

            fileWriter.writeDataIntoOutputFile(weatherCurrent.getCurrentCityData());
        }
    }
}
