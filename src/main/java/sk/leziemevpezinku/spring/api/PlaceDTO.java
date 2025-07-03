package sk.leziemevpezinku.spring.api;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PlaceDTO extends EnumerationItemDTO {

    @ToString.Include
    @NotNull
    @DecimalMin("-90")
    @DecimalMax("90")
    @Digits(integer = 3, fraction = 15)
    private BigDecimal latitude;

    @ToString.Include
    @NotNull
    @DecimalMin("-180")
    @DecimalMax("180")
    @Digits(integer = 3, fraction = 15)
    private BigDecimal longitude;
}
