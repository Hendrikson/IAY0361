import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import weather.CurrentWeather;
import weather.ForecastWeather;
import weatherdata.WeatherData;

import java.io.*;

public class WeatherGateway {
    private CurrentWeather currentWeather;
    private ForecastWeather forecastWeather;

    private WeatherGateway(String cityName) throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
        forecastWeather = ForecastWeather.getForecastWeatherByCity(cityName);
    }

    public void getNewCurrentWeather() throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = CurrentWeather.getCurrentWeather();
    }

    public void getNewCurrentWeather(String cityName) throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
    }

    public void getNewForecastWeather() throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        forecastWeather = ForecastWeather.getForecastWeather();
    }

    public void getNewForecastWeather(String cityName) throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        forecastWeather = ForecastWeather.getForecastWeatherByCity(cityName);
    }

    static WeatherGateway getWeatherGatewayByCity() throws  IOException{
        return new WeatherGateway("Tallinn");
    }

    static WeatherGateway getWeatherGatewayByCity(String cityName) throws IOException{
        return new WeatherGateway(cityName);
    }

    static void getWeatherGatewayByCityFromFile(String cityName) throws IOException{
        if (cityName == null || cityName == "") cityName = "Tallinn";
        String inputFileName = "C:\\Users\\Karl\\IdeaProjects\\Automaattestimine\\IAY0361\\src\\src\\input.txt";
        String outputFileName = "C:\\Users\\Karl\\IdeaProjects\\Automaattestimine\\IAY0361\\src\\src\\output.txt";
        BufferedReader br;
        FileReader fr;
        fr = new FileReader(inputFileName);
        br = new BufferedReader(fr);

        String currentLine;
        if ((currentLine = br.readLine()) != null) {
            cityName = currentLine;
        }
        CurrentWeather currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "utf-8"))){
            String outputText = currentWeather.getCurrentCityData();
            String[] outputPieces = outputText.split("\n");
            for (int i = 0; i < outputPieces.length; i++) {
                writer.write(outputPieces[i] + "\r\n");
            }
        }
    }

    JsonObject getForecastObjectFromArray(int index) {
        return forecastWeather.getForecastObjectFromArray(index);
    }

    int getForecastArrayLength() {
        return forecastWeather.getForecastArrayLength();
    }

    double getCurrentTemperature() {
        return currentWeather.getCurrentTemperature();
    }

    double getCurrentTemperatureFromArrayObject(JsonObject obj) { return currentWeather.getCurrentHumidityFromArrayObject(obj); }

    int getCurrentHumidityFromArrayObject(JsonObject obj) { return currentWeather.getCurrentHumidityFromArrayObject(obj); }

    int getCurrentHumidity() { return currentWeather.getCurrentHumidity(); }

    String getCountryCode() {
        return currentWeather.getCountryCode();
    }

    String getCityName() { return currentWeather.getCityName(); }

    String getCurrentCityData() {
        return "City : " + this.getCityName() + "\n" +
                "Current Temperature : " + this.getCurrentTemperature() + "\n" +
                "Current Humidity : " + this.getCurrentHumidity();
    }

    CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    ForecastWeather getForecastWeather() {
        return forecastWeather;
    }

    public static void main(String[] args) throws IOException{
        /*
        String cityName = "Tallinn";
        if (args.length > 0) {
            cityName = args[0];
        }
        WeatherGateway weatherGateway = WeatherGateway.getWeatherGatewayByCity(cityName);
        System.out.println(weatherGateway.getCurrentCityData());
        */
        //getWeatherGatewayByCityFromFile("");
    }
}
