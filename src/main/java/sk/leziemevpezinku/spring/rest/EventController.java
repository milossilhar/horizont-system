package sk.leziemevpezinku.spring.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.repo.EventRepository;

import java.util.List;

@RestController
@RequestMapping(path = "/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    private List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @PostMapping
    private Event createEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }
}
