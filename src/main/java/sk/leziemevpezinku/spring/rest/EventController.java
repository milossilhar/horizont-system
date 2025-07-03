package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.api.EventDTO;
import sk.leziemevpezinku.spring.rest.model.GenericResponse;
import sk.leziemevpezinku.spring.rest.model.PageableResponse;
import sk.leziemevpezinku.spring.service.EventService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EventController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "event")
//@RolesAllowed("ADMIN")
public class EventController {

    public static final String URL = "/events";

    private final EventService eventService;

    @GetMapping
    public PageableResponse<EventDTO> getAll(Pageable pageable) {
        var page = eventService.getPage(pageable);
        return PageableResponse.of(page);
    }

    @GetMapping("/id/{id:\\d+}")
    public EventDTO getEventById(@PathVariable("id") @NotNull @Valid Long id) {
        return eventService.getOne(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventDTO createEvent(@Valid @RequestBody EventDTO event) {
        return eventService.create(event);
    }

    @PutMapping(path = "/{eventId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EventDTO updateEvent(
            @PathVariable("eventId") @NotNull Long eventId,
            @Valid @RequestBody EventDTO event) {
        return eventService.update(event);
    }

    @DeleteMapping(path = "/{eventId:\\d+}")
    public GenericResponse<String> deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.delete(eventId);
        return GenericResponse.of("Successfully deleted.");
    }
}
