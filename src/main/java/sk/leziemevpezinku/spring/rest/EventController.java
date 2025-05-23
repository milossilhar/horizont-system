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
import sk.leziemevpezinku.spring.repo.model.EventTermCapacity;
import sk.leziemevpezinku.spring.rest.model.EventTermCapacityResponse;
import sk.leziemevpezinku.spring.rest.model.GenericResponse;
import sk.leziemevpezinku.spring.service.EventService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(path = "/capacity/{eventUUID}")
    public Map<Long, EventTermCapacityResponse> getCurrentCapacities(
            @PathVariable("eventUUID") @NotNull String eventUUID
    ) {
        List<EventTermCapacity> eventRegistrationCount = eventService.getEventRegistrationCount(eventUUID);

        Map<Long, EventTermCapacityResponse> result = new HashMap<>();
        eventRegistrationCount.forEach(etc -> {
            result.computeIfAbsent(etc.getEventTermId(),
                    (key) -> new EventTermCapacityResponse(etc.getCapacity())
                            .addRegistered(etc.getRegisteredCount())
                            .addConfirmed(etc.getConfirmedCount())
            );

            result.computeIfPresent(etc.getEventTermId(),
                    (key, response) -> response
                            .addRegistered(etc.getRegisteredCount())
                            .addConfirmed(etc.getConfirmedCount())
            );
        });

        return result;
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
