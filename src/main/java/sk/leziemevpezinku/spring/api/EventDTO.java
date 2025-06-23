package sk.leziemevpezinku.spring.api;

import lombok.Builder;
import lombok.Data;
import sk.leziemevpezinku.spring.model.enums.EventType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventDTO {

    private String name;

    private String details;

    private EventType eventType;

    private String imageUrl;

    private LocalDateTime registrationEnds;

    private String placeName;

    private String placeDescription;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String periodName;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    private List<EventConditionDTO> conditions;

    private List<EventTermDTO> terms;
}
