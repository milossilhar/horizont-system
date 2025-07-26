package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.api.enumeration.EventStatus;
import sk.leziemevpezinku.spring.api.validation.EnumerationValue;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for updating all period events with some common attribute
 */
@Data
@Builder
public class PeriodRequestPutDTO {

    @NotNull
    @EnumerationValue(enumName = EnumerationName.REG_PERIOD)
    private String periodCode;

    private EventStatus status;
    private LocalDateTime registrationStarts;
    private LocalDateTime registrationEnds;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean locked;
}
