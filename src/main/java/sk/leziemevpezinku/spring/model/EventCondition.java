package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import sk.leziemevpezinku.spring.model.enums.EventConditionType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EventCondition {

    /** Enumerated: {@link EventConditionType} */
    @ToString.Include
    @Column(name = "condition_type", length = 20, nullable = false)
    private String conditionType;

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
