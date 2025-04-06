package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Event;

import java.util.List;

public interface EventService {

    /**
     * Creates new event.
     * @param event event to create
     * @return created event
     */
    Event createEvent(Event event);

    /**
     * Updates event.
     * @param eventId id of the event
     * @param event object with updated information
     * @return updated event
     */
    Event updateEvent(Long eventId, Event event);

    /**
     * Removes event with given io
     * @param id event id
     * @throws sk.leziemevpezinku.spring.service.exception.CommonException when event with id does not exist
     */
    void removeEvent(Long id);

    /**
     * Finds all events.
     * @return list of all events
     */
    List<Event> getAll();
}
