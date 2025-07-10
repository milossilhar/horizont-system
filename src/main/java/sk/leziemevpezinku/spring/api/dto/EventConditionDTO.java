package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.api.validation.EnumerationValue;

@Data
@Builder
public class EventConditionDTO {

    @NotNull
    @EnumerationValue(enumName = EnumerationName.REG_EVENT_CONDITION_TYPE)
    private String conditionTypeCode;

    @Size(max = 50)
    private String minValue;

    @Size(max = 50)
    private String maxValue;

    @Size(max = 50)
    private String value;
}
