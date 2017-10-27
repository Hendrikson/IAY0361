package weather;

import com.google.gson.JsonObject;
import file.FileReader;

import java.io.*;

public class WeatherData {
    private JsonObject weatherData;

    public WeatherData() throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    public WeatherData(String cityName) throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    /*
    public static WeatherData getCurrentWeather() throws IOException{
        return new WeatherData();
    }

    public static WeatherData getCurrentWeatherByCity(String cityName) throws IOException{
        return new WeatherData(cityName);
    }
    */

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


    public void getNewWeatherData() throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    public void getNewWeatherData(String cityName) throws IOException{
        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    public void getNewWeatherDataFromFile() throws IOException {
        FileReader fileReader = new FileReader();
        String cityName = fileReader.readCityFromInput();

        String weatherDataUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.weatherData = weatherData.getJsonData(weatherDataUrl).getAsJsonObject();
    }

    public double getCurrentTemperature() {
        return weatherData.get("main").getAsJsonObject().get("temp").getAsDouble();
    }

    public int getCurrentHumidity() {
        return weatherData.get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    public String getCountryCode() {
        return weatherData.get("sys").getAsJsonObject().get("country").getAsString();
    }

    public String getCityName() { return weatherData.get("name").getAsString(); }

    public String getCurrentCityData() {
        return "City : " + this.getCityName() + " \n" +
                "Coordinates : " + this.getCoordinatesAsString() + "\n" +
                "Current Temperature : " + this.getCurrentTemperature() + " \n" +
                "Current Humidity : " + this.getCurrentHumidity();
    }

    public void writeCurrentCityDataToFile() {
        file.FileWriter fileWriter = new file.FileWriter();
        fileWriter.writeDataIntoOutput(this.getCurrentCityData());
    }

    public boolean equals(WeatherData weatherData) {
        return weatherData != null &&
                this.getCityName().equals(weatherData.getCityName()) &&
                this.getCurrentTemperature() == weatherData.getCurrentTemperature() &&
                this.getCurrentHumidity() == weatherData.getCurrentHumidity() &&
                this.getCountryCode().equals(weatherData.getCountryCode());
    }

    public double getLongitudeAsDouble() {
        return weatherData.get("coord").getAsJsonObject().get("lon").getAsDouble();
    }

    public double getLatitudeAsDouble() {
        return weatherData.get("coord").getAsJsonObject().get("lat").getAsDouble();
    }

    public String getCoordinatesAsString() {
        return "(" + this.getLongitudeAsDouble() + " " +
                this.getLatitudeAsDouble() + ")";
    }
}
