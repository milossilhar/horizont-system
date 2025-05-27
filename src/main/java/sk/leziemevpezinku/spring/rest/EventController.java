package sk.leziemevpezinku.spring.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.model.Views;
import sk.leziemevpezinku.spring.rest.model.GenericResponse;
import sk.leziemevpezinku.spring.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "event")
@RolesAllowed("ADMIN")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @JsonView(Views.EventInternal.class)
    public List<Event> getEvents() {
        return eventService.getAll();
    }

    @GetMapping("/detail")
    @JsonView(Views.EventInternal.class)
    public List<Event> getDetailedEvents() {
        return eventService.getAllWithCapacities();
    }

    @GetMapping("/detail/{eventId:\\d+}")
    @JsonView(Views.EventInternal.class)
    public Event getDetailedEvent(@PathVariable("eventId") @NotNull Long eventId) {
        return eventService.getById(eventId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event createEvent(@Valid @RequestBody Event event) {
        throw new UnsupportedOperationException("Not READY");
//        return eventService.createEvent(event);
    }

    @PutMapping(path = "/{eventId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Event updateEvent(@PathVariable("eventId") @NotNull Long eventId, @Valid @RequestBody Event event) {
        throw new UnsupportedOperationException("Not READY");
//        if (!eventId.equals(event.getId())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IDs mismatch");
//        }
//
//        return eventService.updateEvent(eventId, event);
    }

    @DeleteMapping(path = "/{eventId:\\d+}")
    public GenericResponse<String> deleteEvent(@PathVariable("eventId") Long eventId) {
        throw new UnsupportedOperationException("Not READY");

//        eventService.removeEvent(eventId);
//        return new GenericResponse<>("Successfully deleted.");
    }
}
