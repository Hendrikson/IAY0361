package weatherdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class WeatherData {
    private HttpUtility httpUtility = new HttpUtility();

    public WeatherData() {}

    WeatherData(HttpUtility httpUtility) {
        this.httpUtility = httpUtility;
    }

    public JsonObject getJsonData(String Url) throws IOException {
        HttpURLConnection request = httpUtility.makeHttpGetRequest(Url);
        request.connect();
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject weatherData = root.getAsJsonObject();
        request.disconnect();
        return weatherData;
    }

    public static void main(String[] args) throws IOException {
        WeatherData data = new WeatherData();
        System.out.println(data.getJsonData("http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71").get("main"));
    }
}
