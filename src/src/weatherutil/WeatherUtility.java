package weatherutil;

import com.google.gson.JsonObject;
import file.FileReader;
import file.FileWriter;
import weather.WeatherCurrent;
import weather.WeatherForecast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeatherUtility {
    public static String getCityCurrentData(WeatherCurrent weatherCurrent) {
        return "City : " + weatherCurrent.getCityName() + " \n" +
                "Coordinates : " + weatherCurrent.getCoordinatesAsString() + "\n" +
                "Current Temperature : " + weatherCurrent.getCurrentTemperature() + " \n" +
                "Current Humidity : " + weatherCurrent.getCurrentHumidity();
    }

    public static String getCityForecastData(WeatherForecast weatherForecast) {
        List<JsonObject> forecastObjects = weatherForecast.getAllForecastObjects();
        List<JsonObject> objectsOfCurrentDay = new ArrayList<>();
        StringBuilder returnString = new StringBuilder("");
        Boolean newDay = false;
        Integer lastTime = Integer.parseInt(forecastObjects.get(0).getAsJsonObject().get("dt_txt").getAsString().split(" ")[1].split(":")[0]);
        forecastObjects.remove(0);
        Integer dayCount = 1;
        for(JsonObject wobj:forecastObjects) {
            if (!newDay) {
                Integer currentTime = Integer.parseInt(wobj.get("dt_txt").getAsString().split(" ")[1].split(":")[0]);
                if (currentTime >= 0 && lastTime > currentTime) {
                    newDay = true;
                } else {
                    objectsOfCurrentDay.add(wobj);
                }
            } else {
                List<Integer> maxTemps = new ArrayList<>();
                List<Integer> minTemps = new ArrayList<>();
                if (objectsOfCurrentDay.size() != 0) {
                    for (JsonObject weatherObj : objectsOfCurrentDay) {
                        maxTemps.add(weatherObj.get("main").getAsJsonObject().get("temp_max").getAsInt());
                        minTemps.add(weatherObj.get("main").getAsJsonObject().get("temp_min").getAsInt());
                    }
                    returnString.append("Day : ").append(dayCount).append("\n").append("    Max Temp : ")
                            .append(Collections.max(maxTemps)).append("\n").append("    Min Temp : ")
                            .append(Collections.min(minTemps)).append("\n");
                }
                newDay = false;
                objectsOfCurrentDay = new ArrayList<>();
                objectsOfCurrentDay.add(wobj);
                dayCount += 1;
                if (dayCount > 3) break;
            }
            lastTime = Integer.parseInt(wobj.get("dt_txt").getAsString().split(" ")[1].split(":")[0]);
        }
        return returnString.toString();
    }

    public static String getCityBothData(WeatherCurrent weatherCurrent, WeatherForecast weatherForecast) {
        StringBuilder finalString = new StringBuilder("City : " + weatherCurrent.getCityName() + "\n" +
                "Coordinates : " + weatherCurrent.getCoordinatesAsString() + "\n");
        finalString.append(WeatherUtility.getCityForecastData(weatherForecast));
        finalString.append("Current Temp : ").append(weatherCurrent.getCurrentTemperature());
        return finalString.toString();
    }

    public static void main(String[] args) throws IOException {
        WeatherUtility.writeToFileAllCitiesData();
    }

    public static void writeToFileAllCitiesData() throws IOException {
        FileReader fileReader = new FileReader();
        List<String> cities = fileReader.readCitiesFromInput();
        List<String> citiesData = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            WeatherCurrent weatherCurrent = new WeatherCurrent(cities.get(i));
            WeatherForecast weatherForecast = new WeatherForecast(cities.get(i));

            FileWriter fileWriter = new FileWriter(weatherCurrent.getCityName());
            System.out.println(WeatherUtility.getCityBothData(weatherCurrent, weatherForecast));
            fileWriter.writeDataIntoOutputFile(WeatherUtility.getCityBothData(weatherCurrent, weatherForecast));
        }
    }
}
