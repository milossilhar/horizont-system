package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EventCondition {

    /** Enumerated: REG_EVENT_CONDITION_TYPE */
    @ToString.Include
    @Column(name = "condition_type_code", length = 10, nullable = false)
    private String conditionTypeCode;

    @ToString.Include
    @Column(name = "min_value", length = 50)
    private String minValue;

    @ToString.Include
    @Column(name = "max_value", length = 50)
    private String maxValue;

    @ToString.Include
    @Column(name = "value", length = 50)
    private String value;
}
