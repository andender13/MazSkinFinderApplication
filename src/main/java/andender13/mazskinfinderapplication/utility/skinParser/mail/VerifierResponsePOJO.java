package andender13.mazskinfinderapplication.utility.skinParser.mail;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifierResponsePOJO {
    @JsonProperty("data")
    private DataJson data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataJson {
        @JsonProperty("status")
        private String status;
        @JsonProperty("result")
        private String result;
    }
}
