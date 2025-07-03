package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import sk.leziemevpezinku.spring.api.validation.AccentedName;
import sk.leziemevpezinku.spring.api.validation.StartBeforeEnd;

import java.time.LocalDate;

@Data
@Builder
@ToString
@StartBeforeEnd(startProperty = "startDate", endProperty = "endDate")
public class PeriodDTO {

    @Positive
    private Long id;

    @NotNull
    @AccentedName
    @Size(max = 100)
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;
}
