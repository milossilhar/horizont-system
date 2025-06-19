package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@RequiredArgsConstructor
public class EventServiceBean implements EventService {

    private final EventRepository eventRepository;

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
    public Event getById(Long id) {
        return findByID(id);
    }

    @Override
    public Event getByUUID(String uuid) {
        return findByUUID(uuid);
    }

    @Override
    public List<Event> getAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public Page<Event> getAll(Pageable page) {
        return this.eventRepository.findAll(page);
    }

    @Override
    public List<Event> getAllWithCapacities() {
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
    public List<EventTermCapacity> getEventRegistrationCount(String eventUUID) {
        Event event = findByUUID(eventUUID);

        return eventRepository.countRegistrations(event.getId());
    }

    private Event findByUUID(String uuid) {
        Optional<Event> eventOptional = eventRepository.findLoadedByUuid(uuid);

        if (eventOptional.isEmpty()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                    .build();
        }

        return eventOptional.get();
    }

    private Event findByID(Long id) {
        Optional<Event> eventOptional = eventRepository.findLoadedById(id);

        if (eventOptional.isEmpty()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_NOT_FOUND_EVENT)
                    .build();
        }

        return eventOptional.get();
    }
}
