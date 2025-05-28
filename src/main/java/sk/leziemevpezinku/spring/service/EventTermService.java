package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.EventTerm;

import java.util.List;

public interface EventTermService {

    /**
     * Finds event term by given ID, loaded with registrations.
     * @param eventTermId event term id
     * @return found event term
     */
    EventTerm getById(Long eventTermId);

    /**
     * Finds all currently active terms
     * @return list of active event terms
     */
    List<EventTerm> getCurrent();
}
