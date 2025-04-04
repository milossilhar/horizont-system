package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EventCondition {

    // enum - REG_E_EVENT_CONDITION_TYPE
    @Column(name = "condition_type", length = 10)
    private String conditionType;

    @Column(name = "min_value")
    private BigDecimal minValue;

    @Column(name = "max_value")
    private BigDecimal maxValue;

    @Column(name = "condition_value", length = 50)
    private String conditionValue;
}
