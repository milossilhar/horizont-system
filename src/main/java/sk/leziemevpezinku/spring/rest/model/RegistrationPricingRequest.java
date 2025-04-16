package sk.leziemevpezinku.spring.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPricingRequest {

    @NotNull
    @Positive
    @JsonProperty("eventTermId")
    private Long eventTermId;

    @NotNull
    @Email
    @JsonProperty("userEmail")
    private String userEmail;

    @NotNull
    @Positive
    @JsonProperty("numberOfPeople")
    private Long numberOfPeople;
}
