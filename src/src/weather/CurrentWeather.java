package weather;

import com.google.gson.JsonObject;
import weatherdata.WeatherData;

import java.io.*;

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

    public static void writeCityToFile(String cityName) throws IOException{
        String inputFileName = "C:\\Users\\Karl\\IdeaProjects\\Automaattestimine\\IAY0361\\src\\src\\input.txt";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inputFileName), "utf-8"))){
            writer.write(cityName);
        }
    }

    public static String readCityFromFile() throws IOException {
        String inputFileName = "C:\\Users\\Karl\\IdeaProjects\\Automaattestimine\\IAY0361\\src\\src\\input.txt";
        String cityName = "Tallinn";

        BufferedReader br;
        FileReader fr;
        fr = new FileReader(inputFileName);
        br = new BufferedReader(fr);

        String currentLine;
        if ((currentLine = br.readLine()) != null) {
            cityName = currentLine;
        }
        return cityName;
    }

    public static CurrentWeather getCurrentWeatherFromFile() throws IOException{
        String cityName = readCityFromFile();
        if (cityName == null || cityName.equals("")) cityName = "Tallinn";
        return new CurrentWeather(cityName);
    }

    public static void writeCityDataIntoFile() throws IOException{
        String cityName = readCityFromFile();
        if (cityName == null || cityName.equals("")) cityName = "Tallinn";
        String outputFileName = "C:\\Users\\Karl\\IdeaProjects\\Automaattestimine\\IAY0361\\src\\src\\output.txt";

        CurrentWeather currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "utf-8"))){
            String outputText = currentWeather.getCurrentCityData();
            String[] outputPieces = outputText.split("\n");
            for (String outputPiece : outputPieces) {
                writer.write(outputPiece + "\r\n");
            }
        }
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
        return "City : " + this.getCityName() + " \n" +
                "Current Temperature : " + this.getCurrentTemperature() + " \n" +
                "Current Humidity : " + this.getCurrentHumidity();
    }

    public static void main(String[] args) throws IOException{
        String cityName = "Tallinn";
        if (args.length > 0) {
            cityName = args[0];
        }
        CurrentWeather currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
        System.out.println(currentWeather.getCurrentCityData());
    }

    public boolean equals(CurrentWeather currentWeather) {
        return currentWeather != null &&
                this.getCityName().equals(currentWeather.getCityName()) &&
                this.getCurrentTemperature() == currentWeather.getCurrentTemperature() &&
                this.getCurrentHumidity() == currentWeather.getCurrentHumidity() &&
                this.getCountryCode().equals(currentWeather.getCountryCode());
    }
}
