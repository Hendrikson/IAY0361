package weather;

import com.google.gson.JsonObject;
import weatherdata.WeatherData;

import java.io.IOException;

public class CurrentWeather {
    private JsonObject currentWeather;

    private CurrentWeather() throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
    }

    private CurrentWeather(String cityName) throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
    }

    public static CurrentWeather getCurrentWeather() throws IOException{
        return new CurrentWeather();
    }

    public static CurrentWeather getCurrentWeatherByCity(String cityName) throws IOException{
        return new CurrentWeather(cityName);
    }

    public void getNewCurrentWeather() throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
    }

    public void getNewCurrentWeather(String cityName) throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
    }

    public double getCurrentTemperature() {
        return currentWeather.get("main").getAsJsonObject().get("temp").getAsDouble();
    }

    public int getCurrentHumidity() {
        return currentWeather.get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    public String getCountryCode() {
        return currentWeather.get("sys").getAsJsonObject().get("country").getAsString();
    }

    public String getCityName() { return currentWeather.get("name").getAsString(); }

    public String getCurrentCityData() {
        return "City : " + this.getCityName() + "\n" +
                "Current Temperature : " + this.getCurrentTemperature() + "\n" +
                "Current Humidity : " + this.getCurrentHumidity();
    }

    public int getCurrentHumidityFromArrayObject(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    public static void main(String[] args) throws IOException{
        String cityName = "Tallinn";
        if (args.length > 0) {
            cityName = args[0];
        }
        CurrentWeather currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
        System.out.println(currentWeather.getCurrentCityData());
    }
}
