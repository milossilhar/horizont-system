package sk.leziemevpezinku.spring.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import sk.leziemevpezinku.spring.api.validation.AccentedName;
import sk.leziemevpezinku.spring.api.validation.FreeText;
import sk.leziemevpezinku.spring.api.validation.SystemName;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type",
        defaultImpl = EnumerationItemDTO.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlaceDTO.class, name = "place"),
})
public class EnumerationItemDTO {

    @Positive
    private Long id;

    @ToString.Include
    @NotEmpty
    @SystemName
    private String code;

    @ToString.Include
    @NotEmpty
    @AccentedName
    @Size(max = 100)
    private String name;

    @FreeText
    @Size(max = 150)
    private String description;

    @NotNull
    @PositiveOrZero
    private Integer ordering;

    private Boolean hidden;
}
