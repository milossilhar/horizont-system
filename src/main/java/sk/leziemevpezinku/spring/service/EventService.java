package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.repo.model.EventTermCapacity;

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
     * Gets event by id.
     * @param id id of an event
     * @return found event
     * @throws sk.leziemevpezinku.spring.service.exception.CommonException when event with given uuid is not found
     */
    Event getById(Long id);

    /**
     * Gets event by uuid.
     * @param uuid uuid of an event
     * @return found event
     * @throws sk.leziemevpezinku.spring.service.exception.CommonException when event with given uuid is not found
     */
    Event getByUUID(String uuid);

    /**
     * Finds all events.
     * @return list of all events
     */
    List<Event> getAll();

    /**
     * Finds all events and adds computed current registration capacities.
     * @return list of all events with term capacities
     */
    List<Event> getAllWithCapacities();

    /**
     * Finds current and future events.
     * @return list of current and future events
     */
    List<Event> getCurrentAndFuture();

    /**
     * Counts all registrations for Event.
     * @param eventUUID uuid of an event
     * @return counted registrations by eventTerm
     */
    List<EventTermCapacity> getEventRegistrationCount(String eventUUID);
}
