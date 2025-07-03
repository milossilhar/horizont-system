package sk.leziemevpezinku.spring.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppParamDTO {

    @NotNull
    @Size(max = 100)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Size(max = 200)
    @JsonProperty("value")
    private String value;
}
