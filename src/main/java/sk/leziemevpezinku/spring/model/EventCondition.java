package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("condition_type")
    @Column(name = "condition_type", length = 10)
    private String conditionType;

    @JsonProperty("min_value")
    @Column(name = "min_value")
    private BigDecimal minValue;

    @JsonProperty("max_value")
    @Column(name = "max_value")
    private BigDecimal maxValue;

    @JsonProperty("condition_value")
    @Column(name = "condition_value", length = 50)
    private String conditionValue;
}
