package sk.leziemevpezinku.spring.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.model.enums.CapacityStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class EventTermDTO {

    private LocalDate startDate;

    private LocalDate endDate;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    @Schema(description = "duration of the event term in minutes")
    private Integer duration;

    private Long numberOfLessons;

    private List<String> expectedTrainers;

    @Builder.Default
    private BigDecimal deposit = BigDecimal.ZERO;

    @Builder.Default
    private Long capacity = 0L;

    @Builder.Default
    private Long registered = 0L;

    @JsonGetter("available")
    public Long getAvailable() {
        if (capacity == null) return 0L;
        return capacity - Objects.requireNonNullElse(registered, 0L);
    }

    @JsonGetter("capacityStatus")
    public CapacityStatus getCapacityStatus() {
        Objects.requireNonNull(registered);
        Objects.requireNonNull(capacity);

        if (registered >= capacity) {
            return CapacityStatus.FILLED;
        } else if (registered + 1 == capacity) {
            return CapacityStatus.LAST_ONE;
        } else {
            var available = capacity - registered;
            var remainsPercent = BigDecimal.valueOf(available)
                    .divide(BigDecimal.valueOf(capacity), RoundingMode.HALF_DOWN)
                    .multiply(BigDecimal.valueOf(100L));

            if (remainsPercent.compareTo(BigDecimal.valueOf(CapacityStatus.ALMOST_FILLED.getPercentThreshold())) <= 0) {
                return CapacityStatus.ALMOST_FILLED;
            } else if (remainsPercent.compareTo(BigDecimal.valueOf(CapacityStatus.FILLING.getPercentThreshold())) <= 0) {
                return CapacityStatus.FILLING;
            }
        }

        return CapacityStatus.FREE;
    }
}
