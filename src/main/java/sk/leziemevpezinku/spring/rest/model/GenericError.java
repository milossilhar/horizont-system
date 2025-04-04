package sk.leziemevpezinku.spring.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericError {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;
}
