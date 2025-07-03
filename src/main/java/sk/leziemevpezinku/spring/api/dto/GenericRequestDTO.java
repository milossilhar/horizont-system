package sk.leziemevpezinku.spring.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericRequestDTO<T> {

    @JsonProperty("value")
    private T value;
}
