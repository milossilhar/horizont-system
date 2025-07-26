package sk.leziemevpezinku.spring.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.api.validation.AccentedName;
import sk.leziemevpezinku.spring.api.validation.FreeText;
import sk.leziemevpezinku.spring.api.validation.StartBeforeEnd;
import sk.leziemevpezinku.spring.api.validation.UrlPath;

import java.util.List;

@Data
@Builder
@StartBeforeEnd(startProperty = "registrationStarts", endProperty = "registrationEnds")
public class EcaEventDTO {

    @NotNull
    @AccentedName
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @FreeText
    @Size(min = 1, max = 2000)
    private String details;

    @UrlPath
    @Size(min = 1, max = 100)
    private String imageUrl;

    private List<EventConditionDTO> conditions;

    private List<EcaEventTermDTO> terms;
}
