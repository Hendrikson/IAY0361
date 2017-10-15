package weather;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import weatherdata.WeatherData;

import java.io.*;

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
        weatherObject = weatherData.getJsonData(forecastWeatherUrl);
        forecastWeather = weatherObject.get("list").getAsJsonArray();
    }

    public void getNewForecastWeather(String cityName) throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        weatherObject = weatherData.getJsonData(forecastWeatherUrl);
        forecastWeather = weatherObject.get("list").getAsJsonArray();
    }

    public JsonObject getForecastObjectFromArray(int index) {
        return forecastWeather.get(index).getAsJsonObject();
    }

    public int getHumidityFromArrayObject(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    public int getHumidityFromArrayObject(int index) {
        return forecastWeather.get(index).getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt();
    }

    public double getTemperatureFromArrayObject(JsonObject obj) {
        return obj.get("main").getAsJsonObject().get("temp").getAsDouble();
    }

    public double getTemperatureFromArrayObject(int index) {
        return forecastWeather.get(index).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
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
        System.out.println(forecastWeather.getHumidityFromArrayObject(0));
        System.out.println(forecastWeather.getTemperatureFromArrayObject(0));
    }

    static void writeCityToFile(String cityName) throws IOException{
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

    public String getCountryCode() {
        return weatherObject.get("city").getAsJsonObject().get("country").getAsString();
    }

    public boolean equals(ForecastWeather forecastWeather) {
        return forecastWeather != null &&
                this.getCityName().equals(forecastWeather.getCityName()) &&
                this.getCountryCode().equals(forecastWeather.getCountryCode());
    }
}
