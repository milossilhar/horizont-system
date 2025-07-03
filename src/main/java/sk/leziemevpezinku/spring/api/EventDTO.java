package sk.leziemevpezinku.spring.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.api.validation.*;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;
import sk.leziemevpezinku.spring.model.enums.EventStatus;
import sk.leziemevpezinku.spring.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@StartBeforeEnd(startProperty = "registrationStarts", endProperty = "registrationEnds")
public class EventDTO {

    @Positive
    private Long id;

    @NotNull
    @AccentedName
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @FreeText
    @Size(min = 1, max = 2000)
    private String details;

    @NotNull
    @EnumerationValue(enumName = EnumerationName.REG_EVENT_TYPE)
    private String eventType;

    @UrlPath
    @Size(min = 1, max = 100)
    private String imageUrl;

    @NotNull
    private LocalDateTime registrationStarts;

    @NotNull
    private LocalDateTime registrationEnds;

    @NotNull
    @EnumerationValue(enumName = EnumerationName.REG_PLACE)
    private String placeCode;

    @RequiredForeignKey
    private Long periodId;

    private List<EventConditionDTO> conditions;

    /** updatable properties with custom logic see {@link EventService} */
    private LocalDateTime locked;
    private EventStatus status;

    /** non-updatable properties */
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
