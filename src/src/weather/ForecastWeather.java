package weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import weatherdata.WeatherData;

import java.io.*;
import java.nio.file.Paths;

public class ForecastWeather {
    private JsonArray forecastWeather;
    private JsonObject weatherObject;
    private static final String inputUrl = Paths.get("src\\src\\input.txt").toAbsolutePath().toString();
    private static final String outputUrl = Paths.get("src\\src\\output.txt").toAbsolutePath().toString();

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

    public static void writeCityToFile(String cityName) throws IOException{
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inputUrl), "utf-8"))){
            writer.write(cityName);
        } catch (Exception e) {
            throw new RuntimeException("File not found! Path used : " + inputUrl);
        }
    }

    public static String readCityFromFile() throws IOException {
        String cityName = "Tallinn";

        BufferedReader br;
        FileReader fr;
        try {
            fr = new FileReader(inputUrl);
            br = new BufferedReader(fr);

            String currentLine;
            if ((currentLine = br.readLine()) != null) {
                cityName = currentLine;
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found! Path used : " + inputUrl);
        }
        return cityName;
    }

    public static ForecastWeather getForecastWeatherFromFile() throws IOException{
        String cityName = readCityFromFile();
        if (cityName == null || cityName.equals("")) cityName = "Tallinn";
        return new ForecastWeather(cityName);
    }

    public static void writeCityDataIntoFile() throws IOException{
        String cityName = readCityFromFile();
        if (cityName == null || cityName.equals("")) cityName = "Tallinn";

        ForecastWeather forecastWeather = ForecastWeather.getForecastWeatherByCity(cityName);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputUrl), "utf-8"))){
            String outputText = forecastWeather.getCurrentCityData();
            String[] outputPieces = outputText.split("\n");
            for (String outputPiece : outputPieces) {
                writer.write(outputPiece + "\r\n");
            }
        }
    }

    public String getCityName() { return weatherObject.get("city").getAsJsonObject().get("name").getAsString(); }

    public String getCurrentCityData() {
        StringBuilder returnStr = new StringBuilder("City : " + this.getCityName() + "\n" +
                "Highest Temp : " + this.getHighestTemperatureFromArray() + "\n" +
                "Lowest Temp : " + this.getLowestTemperatureFromArray() + "\n" +
                "Highest Humidity : " + this.getHighestHumidityFromArray() + "\n" +
                "Lowest Temperature : " + this.getLowestHumidityFromArray() + "\n" +
                "Array Length : " + this.getForecastArrayLength() + "\n");
        for (int i = 0; i < this.getForecastArrayLength(); i++) {
            returnStr.append(" ").append(i).append(" : ").append(forecastWeather.get(i).getAsJsonObject().get("dt").getAsString()).append("\n").append("  Humidity : ").append(this.getHumidityFromArrayObject(i)).append("\n").append("  Temperature : ").append(this.getTemperatureFromArrayObject(i)).append("\n");
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

    /*
    public JsonObject getForecastObjectFromArray(int index) {
        return forecastWeather.get(index).getAsJsonObject();
    }
    */

    /*
    public int getHumidityFromArrayObject(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("humidity").getAsInt();
    }
    */

    public int getHumidityFromArrayObject(int index) {
        return forecastWeather.get(index).getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    /*
    public double getTemperatureFromArrayObject(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("temp").getAsDouble();
    }
    */

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

    public int getHighestHumidityFromArray() {
        int currentHighest = this.getHumidityFromArrayObject(0);
        for (int i = 1; i < this.getForecastArrayLength(); i++) {
            int currentHumidity = this.getHumidityFromArrayObject(i);
            if (currentHighest < currentHumidity) currentHighest = currentHumidity;
        }
        return currentHighest;
    }

    public int getLowestHumidityFromArray() {
        int currentLowest = this.getHumidityFromArrayObject(0);
        for (int i = 1; i < this.getForecastArrayLength(); i++) {
            int currentHumidity = this.getHumidityFromArrayObject(i);
            if (currentLowest > currentHumidity) currentLowest = currentHumidity;
        }
        return currentLowest;
    }

    public int getForecastArrayLength() {
        return forecastWeather.size();
    }

    public static void main(String[] args) throws IOException{
        String cityName = "Tallinn";
        if (args.length > 0) {
            cityName = args[0];
        }
        ForecastWeather forecastWeather = ForecastWeather.getForecastWeatherByCity(cityName);
        System.out.println(forecastWeather.getCurrentCityData());
        ForecastWeather.writeCityToFile("Pärnu");
        ForecastWeather.writeCityDataIntoFile();
        //String url = System.getProperty("user.dir");
    }

    public String getCountryCode() {
        return weatherObject.get("city").getAsJsonObject().get("country").getAsString();
    }

    public boolean equals(ForecastWeather forecastWeather) {
        return forecastWeather != null &&
                this.getCityName().equals(forecastWeather.getCityName()) &&
                this.getCountryCode().equals(forecastWeather.getCountryCode());
    }
}
