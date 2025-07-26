package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import sk.leziemevpezinku.spring.api.validation.RequiredForeignKey;
import sk.leziemevpezinku.spring.api.validation.ValidRepeatType;
import sk.leziemevpezinku.spring.api.enumeration.EventTermRepeatType;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@ValidRepeatType
public class EventTermDTO {

    @Positive
    private Long id;

    @NotNull
    private EventTermRepeatType repeatType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    @Positive
    private Integer durationMinutes;

    private LocalDate endDate;

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

    @NotNull
    private Boolean hasAttendance;

    @RequiredForeignKey
    private Long eventId;

//    @JsonGetter("available")
//    public Long getAvailable() {
//        if (capacity == null || registered == null) return null;
//
//        return capacity - registered;
//    }
//
//    @JsonGetter("capacityStatus")
//    public CapacityStatus getCapacityStatus() {
//        if (capacity == null || registered == null) return CapacityStatus.FREE;
//
//        if (registered >= capacity) {
//            return CapacityStatus.FILLED;
//        } else if (registered + 1 == capacity) {
//            return CapacityStatus.LAST_ONE;
//        } else {
//            var available = capacity - registered;
//            var remainsPercent = BigDecimal.valueOf(available)
//                    .divide(BigDecimal.valueOf(capacity), RoundingMode.HALF_DOWN)
//                    .multiply(BigDecimal.valueOf(100L));
//
//            if (remainsPercent.compareTo(BigDecimal.valueOf(CapacityStatus.ALMOST_FILLED.getPercentThreshold())) <= 0) {
//                return CapacityStatus.ALMOST_FILLED;
//            } else if (remainsPercent.compareTo(BigDecimal.valueOf(CapacityStatus.FILLING.getPercentThreshold())) <= 0) {
//                return CapacityStatus.FILLING;
//            }
//        }
//
//        return CapacityStatus.FREE;
//    }
}
