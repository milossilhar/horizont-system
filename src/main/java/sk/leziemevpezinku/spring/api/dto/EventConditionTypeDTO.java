package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import sk.leziemevpezinku.spring.api.enumeration.EventConditionTypeOption;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class EventConditionTypeDTO extends EnumerationItemDTO {

    @ToString.Include
    @NotNull
    private EventConditionTypeOption conditionOption;
}
