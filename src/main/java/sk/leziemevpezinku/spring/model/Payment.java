package sk.leziemevpezinku.spring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

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

    @JsonProperty("deposit")
    @Column(name = "deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal deposit = BigDecimal.ZERO;

    @JsonProperty("discount_value")
    @Column(name = "discount_value", precision = 8, scale = 2)
    private BigDecimal discountValue;

    @JsonProperty("discount_percent")
    @Column(name = "discount_percent", precision = 4, scale = 1)
    private BigDecimal discountPercent;

    @JsonProperty("deposit_paid")
    @Column(name = "deposit_paid")
    private Boolean depositPaid;

    @JsonProperty("paid")
    @Column(name = "paid")
    private Boolean paid;

    // soft reference
    @NotNull
    @JsonProperty("registration_id")
    @Column(name = "registration_id", nullable = false)
    private Long registrationId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @JsonGetter("final_price")
    public BigDecimal getFinalPrice() {
        if (discountValue != null) {
            return price.subtract(discountValue);
        }
        if (discountPercent != null) {
            return price.divide(discountPercent, 2, RoundingMode.HALF_DOWN);
        }
        return price;
    }

    @JsonGetter("has_deposit")
    public Boolean hasDeposit() {
        return this.deposit != null && this.deposit.compareTo(BigDecimal.ZERO) > 0;
    }

    @JsonGetter("remaining_value")
    public BigDecimal getRemainingValue() {
        if (Boolean.TRUE.equals(paid)) return BigDecimal.ZERO;

        BigDecimal finalPrice = getFinalPrice();

        if (Boolean.TRUE.equals(depositPaid)) return finalPrice.subtract(deposit);

        return finalPrice;
    }
}
