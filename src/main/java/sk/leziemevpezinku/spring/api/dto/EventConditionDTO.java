package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.api.enumeration.EventConditionType;

@Data
@Builder
public class EventConditionDTO {

    @NotNull
    private EventConditionType conditionType;

    @Size(max = 50)
    private String minValue;

    @Size(max = 50)
    private String maxValue;

    @Size(max = 50)
    private String value;
}
