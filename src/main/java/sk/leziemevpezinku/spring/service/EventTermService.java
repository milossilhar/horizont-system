package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.model.EventTerm;

public interface EventTermService {

    /**
     * Finds event term by given ID, loaded with registrations.
     * @param eventTermId event term id
     * @return found event term
     */
    EventTerm getById(Long eventTermId);
}
