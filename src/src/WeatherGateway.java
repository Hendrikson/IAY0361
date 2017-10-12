import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

public class WeatherGateway {
    private JsonObject currentWeather;
    private JsonArray forecastWeather;

    private WeatherGateway(String cityName) throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
        forecastWeather = weatherData.getJsonData(forecastWeatherUrl).get("list").getAsJsonArray();
    }

    /*
    private WeatherGateway(JsonObject currentWeather) throws IOException{
        String city = "Tallinn";
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        this.currentWeather = currentWeather;
        forecastWeather = weatherData.getJsonData(forecastWeatherUrl).get("list").getAsJsonArray();
    }

    private WeatherGateway(JsonArray forecastWeather) throws IOException{
        String city = "Tallinn";
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
        this.forecastWeather = forecastWeather;
    }

    private WeatherGateway(JsonObject currentWeather, JsonArray forecastWeather) {


        this.currentWeather = currentWeather;
        this.forecastWeather = forecastWeather;
    }
    */

    static WeatherGateway getWeatherGatewayByCity() throws  IOException{
        return new WeatherGateway("Tallinn");
    }

    static WeatherGateway getWeatherGatewayByCity(String cityName) throws IOException{
        return new WeatherGateway(cityName);
    }

    /*
    public static WeatherGateway getWeatherGatewayByCurrentWeather(JsonObject currentWeather) throws IOException{
        return new WeatherGateway(currentWeather);
    }

    public static WeatherGateway getWeatherGatewayByForecastWeather(JsonArray forecastWeather) throws IOException{
        return new WeatherGateway(forecastWeather);
    }

    public static WeatherGateway getWeatherGatewayByBoth(JsonObject currentWeather, JsonArray forecastWeather) throws IOException{
        return new WeatherGateway(currentWeather, forecastWeather);
    }*/

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

    String getCityName() { return currentWeather.get("name").getAsString(); }

    String getCurrentCityData() {
        return "City : " + this.getCityName() + "\n" +
                "Current Temperature : " + this.getCurrentTemperature() + "\n" +
                "Current Humidity : " + this.getCurrentHumidity();
    }

    public static void main(String[] args) throws IOException{
        String cityName = "Tallinn";
        if (args.length > 0) {
            cityName = args[0];
        }
        WeatherGateway weatherGateway = WeatherGateway.getWeatherGatewayByCity(cityName);
        System.out.println(weatherGateway.getCurrentCityData());
    }
}
