package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class EcaEventTermDTO {

    @NotNull
    private LocalTime startTime;

    @NotNull
    @Positive
    private Integer durationMinutes;

    @NotNull
    private DayOfWeek dayOfWeek;

    @Size(min = 1)
    @UniqueElements
    private List<String> expectedTrainers;

    private List<String> tags;

    @PositiveOrZero
    private BigDecimal deposit;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    @Positive
    private Long capacity;
}
