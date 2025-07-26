package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.api.validation.EnumerationValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for creating ECA events for a given Period
 */
@Data
@Builder
public class EcaRequestPostDTO {

    @NotNull
    @EnumerationValue(enumName = EnumerationName.REG_PERIOD)
    private String periodCode;

    @NotNull
    private LocalDateTime registrationStarts;

    @NotNull
    private LocalDateTime registrationEnds;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Length(min = 1)
    private List<EcaEventDTO> events;
}
