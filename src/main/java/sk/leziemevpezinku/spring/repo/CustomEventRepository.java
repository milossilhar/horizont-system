package sk.leziemevpezinku.spring.repo;

import sk.leziemevpezinku.spring.repo.model.EventTermCapacity;

import java.util.List;

public interface CustomEventRepository {

    /**
     *
     * @param eventId id of the event
     * @return list of capacities for all event terms grouped by status
     */
    List<EventTermCapacity> countRegistrations(Long eventId);
}
