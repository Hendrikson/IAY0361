package weather;

import com.google.gson.JsonObject;
import file.FileReader;
import weatherdata.WeatherData;

import java.io.*;
import java.nio.file.Paths;

public class CurrentWeather {
    private JsonObject currentWeather;
    private static final String inputUrl = Paths.get("src\\input.txt").toAbsolutePath().toString();
    private static final String outputUrl = Paths.get("src\\output.txt").toAbsolutePath().toString();

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

    public void writeCityToFile() throws IOException{
        file.FileWriter fileWriter = new file.FileWriter();
        fileWriter.writeCityIntoInputFile(this.getCityName());
    }

    public String readCityFromFile() throws IOException {
        String cityName;

        FileReader fileReader = new FileReader();
        cityName = fileReader.readCityFromInput();

        return cityName;
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

    public void getNewCurrentWeatherFromFile() throws IOException {
        FileReader fileReader = new FileReader();
        String cityName = fileReader.readCityFromInput();

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
        return "City : " + this.getCityName() + " \n" +
                "Current Temperature : " + this.getCurrentTemperature() + " \n" +
                "Current Humidity : " + this.getCurrentHumidity() + "\n" +
                "Coordinates : " + this.getCoordinatesAsString();
    }

    public void writeCurrentCityDataToFile() {
        file.FileWriter fileWriter = new file.FileWriter();
        fileWriter.writeDataIntoOutput(this.getCurrentCityData());
    }

    public boolean equals(CurrentWeather currentWeather) {
        return currentWeather != null &&
                this.getCityName().equals(currentWeather.getCityName()) &&
                this.getCurrentTemperature() == currentWeather.getCurrentTemperature() &&
                this.getCurrentHumidity() == currentWeather.getCurrentHumidity() &&
                this.getCountryCode().equals(currentWeather.getCountryCode());
    }

    public double getLongitudeAsDouble() {
        return currentWeather.get("coord").getAsJsonObject().get("lon").getAsDouble();
    }

    public double getLatitudeAsDouble() {
        return currentWeather.get("coord").getAsJsonObject().get("lat").getAsDouble();
    }

    public String getCoordinatesAsString() {
        return "(" + this.getLongitudeAsDouble() + " " +
                this.getLatitudeAsDouble() + ")";
    }

    public static String getInputUrl() { return inputUrl; }

    public static String getOutputUrl() { return outputUrl; }
}
