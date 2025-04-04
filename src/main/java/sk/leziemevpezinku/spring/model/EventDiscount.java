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
public class EventDiscount {

    // enum - REG_E_EVENT_DISCOUNT_TYPE
    @Column(name = "discount_type", length = 10)
    private String discountType;

    @Column(name = "condition_value")
    private Integer conditionValue;

    @Column(name = "value")
    private Integer value;

    @Column(name = "percent", precision = 4, scale = 1)
    private BigDecimal percent;
}
