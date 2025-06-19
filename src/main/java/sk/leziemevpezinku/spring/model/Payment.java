package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reg_payment")
public class Payment {

    @Id
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_payment_id")
    @SequenceGenerator(name = "seq_payment_id", sequenceName = "seq_payment_id", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Positive
    @JsonProperty("price")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Builder.Default
    @JsonProperty("deposit")
    @Column(name = "deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal deposit = BigDecimal.ZERO;

    @JsonProperty("discountValue")
    @Column(name = "discount_value", precision = 8, scale = 2)
    private BigDecimal discountValue;

    @JsonProperty("discountPercent")
    @Column(name = "discount_percent", precision = 4, scale = 1)
    private BigDecimal discountPercent;

    @JsonProperty("depositPaid")
    @Column(name = "deposit_paid")
    private Boolean depositPaid;

    @JsonProperty("paid")
    @Column(name = "paid")
    private Boolean paid;

    @JsonGetter("finalPrice")
    public BigDecimal getFinalPrice() {
        if (hasDiscountValue()) return price.subtract(discountValue);
        if (hasDiscountPercent()) return price.subtract(getDiscountPercentValue());
        return price;
    }

    @JsonGetter("hasDiscount")
    public Boolean hasDiscount() {
        return hasDiscountValue() || hasDiscountPercent();
    }

    @JsonGetter("hasDeposit")
    public Boolean hasDeposit() {
        return deposit != null && deposit.compareTo(BigDecimal.ZERO) > 0;
    }

    @JsonGetter("discountPercentValue")
    public BigDecimal getDiscountPercentValue() {
        if (discountPercent == null) return BigDecimal.ZERO;

        return price
                .multiply(discountPercent.divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_DOWN))
                .setScale(2, RoundingMode.HALF_DOWN);
    }

    @JsonGetter("remainingValue")
    public BigDecimal getRemainingValue() {
        if (Boolean.TRUE.equals(paid)) return BigDecimal.ZERO;

        BigDecimal finalPrice = getFinalPrice();

        if (Boolean.TRUE.equals(depositPaid)) return finalPrice.subtract(deposit);

        return finalPrice;
    }

    @JsonGetter("variableSymbol")
    public String getVariableSymbol() {
        return StringUtils.leftPadding(String.valueOf(id), 10, "0");
    }

    private boolean hasDiscountValue() {
        return this.discountValue != null && this.discountValue.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean hasDiscountPercent() {
        return this.discountPercent != null && this.discountPercent.compareTo(BigDecimal.ZERO) > 0;
    }
}
