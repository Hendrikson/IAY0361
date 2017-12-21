package weather;

import com.google.gson.JsonObject;
import file.FileReader;
import weatherdata.WeatherData;

import java.io.*;

public class WeatherCurrent {
    private JsonObject weatherData;

    public WeatherCurrent() throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    public WeatherCurrent(String cityName) throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    public WeatherCurrent(WeatherData weatherData) throws IOException {
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    /** METHODS TO FETCH NEW WEATHER DATA **/

    public void getNewWeatherData() throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    void getNewWeatherData(String cityName) throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    public void getNewWeatherDataFromFile() throws IOException {
        FileReader fileReader = new FileReader();
        String cityName = fileReader.readCityFromInput();

        this.getNewWeatherData(cityName);
    }

    /** JSON READER METHODS **/

    public double getCurrentTemperature() {
        return weatherData.getAsJsonObject("main").get("temp").getAsDouble();
    }

    public int getCurrentHumidity() {
        return weatherData.getAsJsonObject("main").get("humidity").getAsInt();
    }

    String getCountryCode() {
        return weatherData.getAsJsonObject("sys").get("country").getAsString();
    }

    public String getCityName() { return weatherData.get("name").getAsString(); }

    double getLongitudeAsDouble() {
        return weatherData.getAsJsonObject("coord").get("lon").getAsDouble();
    }

    double getLatitudeAsDouble() {
        return weatherData.getAsJsonObject("coord").get("lat").getAsDouble();
    }

    public String getCoordinatesAsString() {
        return "(" + this.getLongitudeAsDouble() + " " +
                this.getLatitudeAsDouble() + ")";
    }

    /** WEATHER DATA COMPARISON **/

    boolean equals(WeatherCurrent weatherCurrent) {
        return weatherCurrent != null &&
                this.getCityName().equals(weatherCurrent.getCityName()) &&
                this.getCurrentTemperature() == weatherCurrent.getCurrentTemperature() &&
                this.getCurrentHumidity() == weatherCurrent.getCurrentHumidity() &&
                this.getCountryCode().equals(weatherCurrent.getCountryCode());
    }
}
