package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Event;

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
     * @return true if successfully deleted, false if entity not found
     */
    boolean removeEvent(Long id);

    /**
     * Finds all events.
     * @return list of all events
     */
    Iterable<Event> getAll();
}
