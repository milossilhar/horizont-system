package sk.leziemevpezinku.spring.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.EventTerm;

import java.util.Optional;

@Repository
public interface EventTermRepository extends ListCrudRepository<EventTerm, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EventTerm> findForUpdateById(Long id);
}
