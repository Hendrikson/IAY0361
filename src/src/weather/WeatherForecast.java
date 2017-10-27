package weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import weatherdata.WeatherData;

import java.io.*;

public class WeatherForecast {
    private JsonArray weatherForecast;
    private JsonObject weatherObject;

    public WeatherForecast() throws IOException{
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

    /*
    public static WeatherForecast getForecastWeather() throws IOException{
        return new WeatherForecast();
    }

    public static WeatherForecast getForecastWeatherByCity(String cityName) throws IOException{
        return new WeatherForecast(cityName);
    }
    */

    public void getNewWeatherData() throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(weatherDataUrl);
        weatherForecast = weatherObject.get("list").getAsJsonArray();
    }

    public void getNewWeatherData(String cityName) throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(weatherDataUrl);
        weatherForecast = weatherObject.get("list").getAsJsonArray();
    }

    public double getTemperatureFromArrayObject(int index) {
        return weatherForecast.get(index).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
    }

    public double getHighestTemperatureFromArray() {
        double currentHighest = this.getTemperatureFromArrayObject(0);
        for (int i = 1; i < this.getForecastArrayLength(); i++) {
            double currentTemp = this.getTemperatureFromArrayObject(i);
            if (currentHighest < currentTemp) currentHighest = currentTemp;
        }
        return currentHighest;
    }

    public double getLowestTemperatureFromArray() {
        double currentLowest = this.getTemperatureFromArrayObject(0);
        for (int i = 1; i < this.getForecastArrayLength(); i++) {
            double currentTemp = this.getTemperatureFromArrayObject(i);
            if (currentLowest > currentTemp) currentLowest = currentTemp;
        }
        return currentLowest;
    }

    public int getForecastArrayLength() {
        return weatherForecast.size();
    }

    public String getCityName() {
        return weatherObject.getAsJsonObject("city").get("name").getAsString();
    }

    public boolean equals(WeatherForecast weatherForecast) {
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
