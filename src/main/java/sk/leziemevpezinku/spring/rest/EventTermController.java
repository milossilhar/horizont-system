package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.api.dto.EventTermDTO;
import sk.leziemevpezinku.spring.api.dto.GenericResponseDTO;
import sk.leziemevpezinku.spring.service.EventTermService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EventController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "eventTerm")
//@RolesAllowed("ADMIN")
public class EventTermController {

    public static final String PER_EVENT_URL = EventController.URL + "/{eventId:\\d+}/terms";

    private final EventTermService service;

    @GetMapping(path = PER_EVENT_URL)
    public GenericResponseDTO<Long> getEventTerms(
            @PathVariable("eventId") @NotNull Long eventId
    ) {
        return GenericResponseDTO.of(eventId);
    }

    @PostMapping(path = PER_EVENT_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventTermDTO createEventTerm(
            @PathVariable("eventId") @NotNull Long eventId,
            @RequestBody @Valid EventTermDTO dto
    ) {
        dto.setEventId(eventId);
        return service.create(dto);
    }
}
