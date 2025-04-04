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
public class EventDiscount {

    // enum - REG_E_EVENT_DISCOUNT_TYPE
    @JsonProperty("discount_type")
    @Column(name = "discount_type", length = 10)
    private String discountType;

    @JsonProperty("condition_value")
    @Column(name = "condition_value")
    private Integer conditionValue;

    @JsonProperty("value")
    @Column(name = "value")
    private Integer value;

    @JsonProperty("percent")
    @Column(name = "percent", precision = 4, scale = 1)
    private BigDecimal percent;
}
