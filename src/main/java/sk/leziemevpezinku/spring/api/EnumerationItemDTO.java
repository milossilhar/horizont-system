package sk.leziemevpezinku.spring.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.api.validation.SystemName;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;

@Data
@Builder
public class EnumerationItemDTO {

    @NotNull
    private EnumerationName name;

    @NotNull
    @SystemName
    private String code;

    @NotNull
    @Size(max = 150)
    private String description;
}
