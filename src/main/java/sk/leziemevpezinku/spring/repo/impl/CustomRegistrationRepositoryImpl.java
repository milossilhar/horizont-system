package sk.leziemevpezinku.spring.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import sk.leziemevpezinku.spring.model.*;
import sk.leziemevpezinku.spring.model.enums.RegistrationStatus;
import sk.leziemevpezinku.spring.repo.CustomRegistrationRepository;

import java.util.List;

@RequiredArgsConstructor
public class CustomRegistrationRepositoryImpl implements CustomRegistrationRepository {

    private final EntityManager em;

    @Override
    public List<Registration> findForDepositPaymentInfoEmail(Limit limit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Registration> query = cb.createQuery(Registration.class);
        Root<Registration> root = query.from(Registration.class);

        query.where(
                cb.and(
                        cb.equal(root.get(Registration_.status), RegistrationStatus.CONFIRMED)
                )
        );

        query.orderBy(cb.asc(root.get(Registration_.id)));

        if (limit.isLimited()) {
            return em.createQuery(query)
                    .setMaxResults(limit.max())
                    .getResultList();
        }

        return em.createQuery(query).getResultList();
    }
}
