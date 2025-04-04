package sk.leziemevpezinku.spring.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericResponse<T> {

    @JsonProperty("value")
    private T value;
}
