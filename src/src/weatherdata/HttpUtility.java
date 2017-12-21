package weatherdata;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpUtility {

    HttpURLConnection makeHttpGetRequest(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        return (HttpURLConnection) url.openConnection();
    }
}