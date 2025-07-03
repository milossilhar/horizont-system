package sk.leziemevpezinku.spring.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value(staticConstructor = "of")
public class GenericResponse<T> {

    @JsonProperty("value")
    T value;
}
