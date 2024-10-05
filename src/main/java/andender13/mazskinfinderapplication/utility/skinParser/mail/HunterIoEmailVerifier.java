package andender13.mazskinfinderapplication.utility.skinParser.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class HunterIoEmailVerifier {
    @Value("${hunterIo.api.key}")
    private String apiKey;

    public boolean verifyEmail(String email) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.hunter.io/v2/email-verifier?email=" + email + "&api_key=" + apiKey))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            ObjectMapper objectMapper = new ObjectMapper();
            VerifierResponsePOJO verifierResponsePOJO = objectMapper.readValue(response.body(), VerifierResponsePOJO.class);
            if (verifierResponsePOJO != null) {
                return !verifierResponsePOJO.getData().getStatus().equalsIgnoreCase("invalid");
            }
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
