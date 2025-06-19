package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import sk.leziemevpezinku.spring.model.enums.EventConditionType;

@Data
@Embeddable
public class EventCondition {

    /** Enumerated :: {@link EventConditionType} */
    @Column(name = "condition_type", length = 20, nullable = false)
    private String conditionType;

    @Column(name = "min_value", length = 50)
    private String minValue;

    @Column(name = "max_value", length = 50)
    private String maxValue;

    @Column(name = "value", length = 50)
    private String value;
}
