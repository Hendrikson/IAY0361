package weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import weatherdata.WeatherData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
    private JsonArray weatherForecast;
    private JsonObject weatherObject;

    /** BASIC CONSTRUCTORS **/

    WeatherForecast() throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(weatherDataUrl);
        weatherForecast = weatherObject.get("list").getAsJsonArray();
    }

    public WeatherForecast(String cityName) throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(weatherDataUrl);
        weatherForecast = weatherObject.get("list").getAsJsonArray();
    }

    /** METHODS TO FETCH NEW WEATHER DATA **/

    void getNewWeatherData() throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(weatherDataUrl);
        weatherForecast = weatherObject.get("list").getAsJsonArray();
    }

    void getNewWeatherData(String cityName) throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(weatherDataUrl);
        weatherForecast = weatherObject.get("list").getAsJsonArray();
    }

    /** JSON READER METHODS **/

    String getCityName() {
        return weatherObject.getAsJsonObject("city").get("name").getAsString();
    }

    int getForecastArrayLength() {
        return weatherForecast.size();
    }

    double getTemperatureFromArrayObject(int index) {
        return weatherForecast.get(index).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
    }

    double getHighestTemperatureFromArray() {
        double currentHighest = this.getTemperatureFromArrayObject(0);
        for (int i = 1; i < this.getForecastArrayLength(); i++) {
            double currentTemp = this.getTemperatureFromArrayObject(i);
            if (currentHighest < currentTemp) currentHighest = currentTemp;
        }
        return currentHighest;
    }

    double getLowestTemperatureFromArray() {
        double currentLowest = this.getTemperatureFromArrayObject(0);
        for (int i = 1; i < this.getForecastArrayLength(); i++) {
            double currentTemp = this.getTemperatureFromArrayObject(i);
            if (currentLowest > currentTemp) currentLowest = currentTemp;
        }
        return currentLowest;
    }

    public List<JsonObject> getAllForecastObjects() {
        List<JsonObject> forecastObjects = new ArrayList<>();
        for (int i = 0; i < this.getForecastArrayLength(); i++) {
            forecastObjects.add(weatherObject.get("list").getAsJsonArray().get(i).getAsJsonObject());
        }
        return forecastObjects;
    }

    /** WEATHER DATA COMPARISON **/

    boolean equals(WeatherForecast weatherForecast) {
        if (this.getForecastArrayLength() != weatherForecast.getForecastArrayLength()) { return false; }
        for (int i = 0; i < this.getForecastArrayLength(); i++) {
            if (!(this.getTemperatureFromArrayObject(i) == weatherForecast.getTemperatureFromArrayObject(i))) {
                return false;
            }
        }
        return this.getCityName().equals(weatherForecast.getCityName()) &&
                this.getHighestTemperatureFromArray() == weatherForecast.getHighestTemperatureFromArray() &&
                this.getLowestTemperatureFromArray() == weatherForecast.getLowestTemperatureFromArray();
    }
}
