package sk.leziemevpezinku.spring.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value(staticConstructor = "of")
public class GenericResponseDTO<T> {

    @JsonProperty("value")
    T value;
}
