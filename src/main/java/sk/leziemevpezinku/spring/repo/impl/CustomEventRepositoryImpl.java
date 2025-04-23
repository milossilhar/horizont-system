package sk.leziemevpezinku.spring.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import sk.leziemevpezinku.spring.model.*;
import sk.leziemevpezinku.spring.repo.CustomEventRepository;
import sk.leziemevpezinku.spring.repo.model.EventTermCapacity;

import java.util.List;

@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {

    private final EntityManager em;

    @Override
    public List<EventTermCapacity> countRegistrations(Long eventId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EventTermCapacity> query = cb.createQuery(EventTermCapacity.class);
        Root<EventTerm> root = query.from(EventTerm.class);

        ListJoin<EventTerm, Registration> registration = root.join(EventTerm_.registrations, JoinType.LEFT);

        query.multiselect(
                root.get(EventTerm_.ID),
                registration.get(Registration_.status),
                cb.max(root.get(EventTerm_.capacity)),
                cb.count(registration.get(Registration_.id))
        );

        query.where(cb.equal(root.get(EventTerm_.event).get(Event_.id), eventId));

        query.groupBy(root.get(EventTerm_.id), registration.get(Registration_.status));

        query.orderBy(cb.asc(root.get(EventTerm_.id)));

        return em.createQuery(query).getResultList();
    }
}
