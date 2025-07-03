package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.api.EventTermDTO;
import sk.leziemevpezinku.spring.rest.model.GenericResponse;
import sk.leziemevpezinku.spring.service.EventTermService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EventTermController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "eventTerm")
//@RolesAllowed("ADMIN")
public class EventTermController {

    public static final String URL = EventController.URL + "/{eventId:\\d+}/terms";

    private final EventTermService service;

    @GetMapping
    public GenericResponse<Long> getEventTerms(@PathVariable("eventId") @NotNull Long eventId) {
        return GenericResponse.of(eventId);
    }

    @PostMapping
    public EventTermDTO createEventTerm(
            @PathVariable("eventId") @NotNull Long eventId,
            @RequestBody @Valid EventTermDTO dto
    ) {
        dto.setEventId(eventId);
        return service.create(dto);
    }

    @GetMapping("/{id:\\d+}")
    public EventTermDTO getEventTerm(@PathVariable("id") @NotNull Long id) {
        return service.getOne(id);
    }
}
