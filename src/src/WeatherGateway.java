import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

public class WeatherGateway {
    private JsonObject currentWeather;
    private JsonArray forecastWeather;
    private final String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
    private final String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

    private WeatherGateway() throws IOException{
        WeatherData weatherData = new WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
        forecastWeather = weatherData.getJsonData(forecastWeatherUrl).get("list").getAsJsonArray();
    }

    private WeatherGateway(JsonObject currentWeather) throws IOException{
        WeatherData weatherData = new WeatherData();
        this.currentWeather = currentWeather;
        forecastWeather = weatherData.getJsonData(forecastWeatherUrl).get("list").getAsJsonArray();
    }

    private WeatherGateway(JsonArray forecastWeather) throws IOException{
        WeatherData weatherData = new WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
        this.forecastWeather = forecastWeather;
    }

    private WeatherGateway(JsonObject currentWeather, JsonArray forecastWeather) {
        this.currentWeather = currentWeather;
        this.forecastWeather = forecastWeather;
    }

    static WeatherGateway getWeatherGateway() throws IOException{
        return new WeatherGateway();
    }

    public static WeatherGateway getWeatherGatewayByCurrentWeather(JsonObject currentWeather) throws IOException{
        return new WeatherGateway(currentWeather);
    }

    public static WeatherGateway getWeatherGatewayByForecastWeather(JsonArray forecastWeather) throws IOException{
        return new WeatherGateway(forecastWeather);
    }

    public static WeatherGateway getWeatherGatewayByBoth(JsonObject currentWeather, JsonArray forecastWeather) throws IOException{
        return new WeatherGateway(currentWeather, forecastWeather);
    }

    public JsonObject getCurrentWeatherAsJsonObject() {
        return currentWeather;
    }

    public JsonArray getForecastWeatherAsJsonArray() {
        return forecastWeather;
    }

    JsonObject getForecastObjectFromArray(int index) {
        return forecastWeather.get(index).getAsJsonObject();
    }

    int getForecastArrayLength() {
        return forecastWeather.size();
    }

    double getCurrentTemperature() {
        return currentWeather.get("main").getAsJsonObject().get("temp").getAsDouble();
    }

    double getCurrentTemperatureFromArrayObject(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("temp").getAsDouble();
    }

    int getCurrentHumidity() {
        return currentWeather.get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    int getCurrentHumidityFromArrayObject(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    String getCountryCode() {
        return currentWeather.get("sys").getAsJsonObject().get("country").getAsString();
    }
}
