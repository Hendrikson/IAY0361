package weather;

import com.google.gson.JsonObject;
import file.FileReader;

import java.io.*;

public class WeatherCurrent {
    private JsonObject weatherData;

    /** BASIC CONSTRUCTORS **/

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

    /* STATIC FACTORY METHODS (DISABLED) */

    /*
    public static WeatherCurrent getCurrentWeather() throws IOException{
        return new WeatherCurrent();
    }

    public static WeatherCurrent getCurrentWeatherByCity(String cityName) throws IOException{
        return new WeatherCurrent(cityName);
    }
    */

    /* INNER CLASS FILE HANDLING (DISABLED) */

    /*
    public String readCityFromFile() throws IOException {
        String cityName;

        FileReader fileReader = new FileReader();
        cityName = fileReader.readCityFromInput();

        return cityName;
    }

    public void writeCityToFile() throws IOException{
        file.FileWriter fileWriter = new file.FileWriter();
        fileWriter.writeDataIntoInputFile(this.getCityName());
    }

    public void writeCurrentCityDataToFile() {
        file.FileWriter fileWriter = new file.FileWriter();
        fileWriter.writeDataIntoOutputFile(this.getCurrentCityData());
    }*/

    /** METHODS TO FETCH NEW WEATHER DATA **/

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

        this.getNewWeatherData(cityName);
    }

    /** JSON READER METHODS **/

    public double getCurrentTemperature() {
        return weatherData.getAsJsonObject("main").get("temp").getAsDouble();
    }

    public int getCurrentHumidity() {
        return weatherData.getAsJsonObject("main").get("humidity").getAsInt();
    }

    public String getCountryCode() {
        return weatherData.getAsJsonObject("sys").get("country").getAsString();
    }

    public String getCityName() { return weatherData.get("name").getAsString(); }

    public double getLongitudeAsDouble() {
        return weatherData.getAsJsonObject("coord").get("lon").getAsDouble();
    }

    public double getLatitudeAsDouble() {
        return weatherData.getAsJsonObject("coord").get("lat").getAsDouble();
    }

    public String getCoordinatesAsString() {
        return "(" + this.getLongitudeAsDouble() + " " +
                this.getLatitudeAsDouble() + ")";
    }

    public String getCurrentCityData() {
        return "City : " + this.getCityName() + " \n" +
                "Coordinates : " + this.getCoordinatesAsString() + "\n" +
                "Current Temperature : " + this.getCurrentTemperature() + " \n" +
                "Current Humidity : " + this.getCurrentHumidity();
    }

    /** WEATHER DATA COMPARISON **/

    public boolean equals(WeatherCurrent weatherCurrent) {
        return weatherCurrent != null &&
                this.getCityName().equals(weatherCurrent.getCityName()) &&
                this.getCurrentTemperature() == weatherCurrent.getCurrentTemperature() &&
                this.getCurrentHumidity() == weatherCurrent.getCurrentHumidity() &&
                this.getCountryCode().equals(weatherCurrent.getCountryCode());
    }
}
