package weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import weatherdata.WeatherData;

import java.io.*;
import java.nio.file.Paths;

public class ForecastWeather {
    private JsonArray forecastWeather;
    private JsonObject weatherObject;
    private static final String inputUrl = Paths.get("src\\input.txt").toAbsolutePath().toString();
    private static final String outputUrl = Paths.get("src\\output.txt").toAbsolutePath().toString();

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

    public String getCityName() { return weatherObject.get("city").getAsJsonObject().get("name").getAsString(); }

    public String getCurrentCityData() {
        StringBuilder returnStr = new StringBuilder("City : " + this.getCityName() + "\n" +
                "Highest Temp : " + this.getHighestTemperatureFromArray() + "\n" +
                "Lowest Temp : " + this.getLowestTemperatureFromArray() + "\n" +
                "Array Length : " + this.getForecastArrayLength() + "\n");
        for (int i = 0; i < this.getForecastArrayLength(); i++) {
            returnStr.append(" ").append(i).append(" : ").append(forecastWeather.get(i).getAsJsonObject().get("dt").getAsString()).append("\n").append("  Temperature : ").append(this.getTemperatureFromArrayObject(i)).append("\n");
        }
        return returnStr.toString();
    }

    public void getNewForecastWeather() throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(forecastWeatherUrl);
        forecastWeather = weatherObject.get("list").getAsJsonArray();
    }

    public void getNewForecastWeather(String cityName) throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(forecastWeatherUrl);
        forecastWeather = weatherObject.get("list").getAsJsonArray();
    }

    public double getTemperatureFromArrayObject(int index) {
        return forecastWeather.get(index).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
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
        return forecastWeather.size();
    }

    public static String getInputUrl() { return inputUrl; }

    public static String getOutputUrl() { return outputUrl; }
}
