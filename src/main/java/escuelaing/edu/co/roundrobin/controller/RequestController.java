package escuelaing.edu.co.roundrobin.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import escuelaing.edu.co.roundrobin.model.Log;

@RestController
@RequestMapping("/log")
public class RequestController {

    private AtomicInteger index = new AtomicInteger(0);
    private static final Logger LOGGER = Logger.getLogger(RequestController.class.getName());
    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     * Define the list of URLS
     */
    private List<String> logServiceUrls = Arrays.asList(
        "http://logservice1:8080/log/update",
        "http://logservice2:8080/log/update",
        "http://logservice3:8080/log/update"
    );
    /**
     * Class to define the response methodology
     * @param message message to send
     * @return ResponseEntity<String>
     * @throws IOException if there is an error
     */
    @GetMapping("/response")
    public ResponseEntity<String> sendMessage(@RequestParam String value) throws IOException {
        int currentIndex = index.getAndUpdate(i -> (i + 1) % logServiceUrls.size());
        String serviceUrl = logServiceUrls.get(currentIndex);
        String urlWithParams = serviceUrl + "?value=" + URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        URL url = new URL(urlWithParams);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        LOGGER.info("GET Response Code :: " + responseCode);
        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            LOGGER.info("Response: " + response.toString());
            Gson gson = new Gson();
            Type messageListType = new TypeToken<List<Log>>(){}.getType();
            List<Log> messages = gson.fromJson(response.toString(), messageListType);
            String jsonResponse = gson.toJson(messages);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } else {
            LOGGER.info("GET request not worked");
            return new ResponseEntity<>("{\"error\":\"GET request not worked\"}", HttpStatus.BAD_REQUEST);
        }
    }

}
