package sk.leziemevpezinku.spring.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.model.User;

@Data
@Builder
public class RegistrationDTO {

    @JsonProperty("event_term_id")
    private Long eventTermId;

    @JsonProperty("user")
    private User user;
}
