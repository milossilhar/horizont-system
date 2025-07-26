package sk.leziemevpezinku.spring.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericErrorDTO {

    @JsonProperty("code")
    private String code;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("message")
    private String message;

    @Singular
    @JsonProperty("parameters")
    private Map<String, Object> parameters;
}
