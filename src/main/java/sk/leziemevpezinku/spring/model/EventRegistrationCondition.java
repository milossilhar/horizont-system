package sk.leziemevpezinku.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class EventRegistrationCondition {

    @Column(name = "condition_type", length = 20)
    private String conditionType;

    @Column(name = "min_value")
    private BigDecimal minValue;

    @Column(name = "max_value")
    private BigDecimal maxValue;

    @Column(name = "condition_value", length = 50)
    private String value;
}
