package sk.leziemevpezinku.spring.service.impl;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.model.Event_;
import sk.leziemevpezinku.spring.repo.EventRepository;
import sk.leziemevpezinku.spring.repo.model.EventTermCapacity;
import sk.leziemevpezinku.spring.service.EventService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceBean implements EventService {

    private final EventRepository eventRepository;

    public EventServiceBean(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public Event createEvent(Event event) {
        event.getTerms().forEach(term -> term.setEvent(event));

        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event updateEvent(Long eventId, Event event) {
        Optional<Event> dbEventOptional = eventRepository.findById(eventId);

        if (dbEventOptional.isEmpty()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                    .build();
        }

        Event dbEvent = dbEventOptional.get();

        // manually update only selected fields
        dbEvent.setName(event.getName());
        dbEvent.setRegStartAt(event.getRegStartAt());
        dbEvent.setRegEndAt(event.getRegEndAt());
        dbEvent.setDiscountType(event.getDiscountType());

        return eventRepository.save(dbEvent);
    }

    @Override
    @Transactional
    public void removeEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                    .build();
        }

        eventRepository.deleteById(eventId);
    }

    @Override
    public Event getByUUID(String uuid) {
        Optional<Event> eventOptional = eventRepository.findByUuid(uuid);

        if (eventOptional.isEmpty()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                    .build();
        }

        return eventOptional.get();
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getCurrentAndFuture() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findAll(
                (root, query, cb) ->
                        cb.or(
                                cb.and(
                                        cb.lessThanOrEqualTo(root.get(Event_.REG_START_AT), now),
                                        cb.greaterThanOrEqualTo(root.get(Event_.REG_END_AT), now)
                                ),
                                cb.greaterThanOrEqualTo(root.get(Event_.REG_START_AT), now)
                        )
        );
    }

    @Override
    @Transactional
    public List<EventTermCapacity> getEventRegistrationCount(Long eventId) {
        List<EventTermCapacity> eventTermCapacities = eventRepository.countRegistrations(eventId);

        if (eventTermCapacities.isEmpty()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                    .build();
        }

        return eventTermCapacities;
    }
}
