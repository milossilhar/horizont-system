package sk.leziemevpezinku.spring.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.repo.EventRepository;
import sk.leziemevpezinku.spring.service.EventService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceBean implements EventService {

    private final EventRepository eventRepository;

    public EventServiceBean(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Event createEvent(Event event) {
        event.getTerms().forEach(term -> term.setEvent(event));

        return eventRepository.save(event);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Event updateEvent(Long eventId, Event event) {
        Optional<Event> dbEventOptional = eventRepository.findById(eventId);

        if (dbEventOptional.isEmpty()) throw new RuntimeException("NOT FOUND");

        Event dbEvent = dbEventOptional.get();

        // manually update only selected fields
        dbEvent.setName(event.getName());
        dbEvent.setRegStartAt(event.getRegStartAt());
        dbEvent.setRegEndAt(event.getRegEndAt());
        dbEvent.setDiscounts(event.getDiscounts());

        return eventRepository.save(dbEvent);
    }

    @Override
    public void removeEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                    .parameter("id", eventId)
                    .build();
        }

        eventRepository.deleteById(eventId);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
