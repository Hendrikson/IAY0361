package weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import weatherdata.WeatherData;

import java.io.IOException;

public class ForecastWeather {
    private JsonArray forecastWeather;
    private JsonObject weatherObject;

    private ForecastWeather() throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(forecastWeatherUrl);
        forecastWeather = weatherObject.get("list").getAsJsonArray();
    }

    private ForecastWeather(String cityName) throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(forecastWeatherUrl);
        forecastWeather = weatherObject.get("list").getAsJsonArray();
    }

    public static ForecastWeather getForecastWeather() throws IOException{
        return new ForecastWeather();
    }

    public static ForecastWeather getForecastWeatherByCity(String cityName) throws IOException{
        return new ForecastWeather(cityName);
    }

    public void getNewForecastWeather() throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        forecastWeather = weatherData.getJsonData(forecastWeatherUrl).get("list").getAsJsonArray();
    }

    public void getNewForecastWeather(String cityName) throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        forecastWeather = weatherData.getJsonData(forecastWeatherUrl).get("list").getAsJsonArray();
    }

    public JsonObject getForecastObjectFromArray(int index) {
        return forecastWeather.get(index).getAsJsonObject();
    }

    public double getCurrentTemperature(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("temp").getAsDouble();
    }

    public int getCurrentHumidity(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    public int getForecastArrayLength() {
        return forecastWeather.size();
    }

    public String getCityName() { return weatherObject.get("city").getAsJsonObject().get("name").getAsString(); }

    public static void main(String[] args) throws IOException{
        String cityName = "Tallinn";
        if (args.length > 0) {
            cityName = args[0];
        }
        ForecastWeather forecastWeather = ForecastWeather.getForecastWeatherByCity(cityName);
        System.out.println(forecastWeather.getCityName());
        System.out.println(forecastWeather.getCurrentHumidity(forecastWeather.getForecastObjectFromArray(0)));
        System.out.println(forecastWeather.getCurrentTemperature(forecastWeather.getForecastObjectFromArray(0)));
    }
}
