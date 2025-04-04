package sk.leziemevpezinku.spring.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.rest.model.GenericResponse;
import sk.leziemevpezinku.spring.service.EventService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<Event> getEvents() {
        return eventService.getAll();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Event createEvent(@Valid @RequestBody Event event)  {
        return eventService.createEvent(event);
    }

    @PutMapping(
            path = "/{eventId:\\d+}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Event updateEvent(@PathVariable("eventId") Long eventId, @Valid @RequestBody Event event) {
        if (!eventId.equals(event.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IDs mismatch");
        }

        return eventService.updateEvent(eventId, event);
    }

    @DeleteMapping(
            path = "/{eventId:\\d+}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenericResponse<String> deleteEvent(@PathVariable("eventId") Long eventId) {
        boolean deleted = eventService.removeEvent(eventId);

        if (!deleted) throw new ResponseStatusException(NOT_FOUND, "Event with ID " + eventId + " not found");

        return new GenericResponse<>("Successfully deleted.");
    }
}
