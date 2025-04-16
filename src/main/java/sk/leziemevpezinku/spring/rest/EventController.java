package sk.leziemevpezinku.spring.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.model.Views;
import sk.leziemevpezinku.spring.rest.model.GenericResponse;
import sk.leziemevpezinku.spring.service.EventService;

import java.util.List;

@RestController
@RequestMapping(path = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "event")
public class EventController {

    @Autowired
    private EventService eventService;

    @JsonView(Views.Internal.class)
    @GetMapping
    public List<Event> getEvents() {
        return eventService.getAll();
    }

    @JsonView(Views.Public.class)
    @GetMapping(path = "/current")
    public List<Event> getCurrentEvents() {
        return eventService.getCurrentAndFuture();
    }

    @JsonView(Views.Public.class)
    @GetMapping(path = "/uuid/{eventUUID}")
    public Event getEventByUUID(@PathVariable("eventUUID") @NotNull String uuid) {
        return eventService.getByUUID(uuid);
    }

    @PostMapping
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PutMapping(path = "/{eventId:\\d+}")
    public Event updateEvent(@PathVariable("eventId") @NotNull Long eventId, @Valid @RequestBody Event event) {
        if (!eventId.equals(event.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IDs mismatch");
        }

        return eventService.updateEvent(eventId, event);
    }

    @DeleteMapping(path = "/{eventId:\\d+}")
    public GenericResponse<String> deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.removeEvent(eventId);
        return new GenericResponse<>("Successfully deleted.");
    }
}
