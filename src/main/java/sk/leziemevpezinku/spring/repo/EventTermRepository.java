package sk.leziemevpezinku.spring.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.EventTerm;

import java.util.Optional;

@Repository
public interface EventTermRepository extends JpaRepository<EventTerm, Long>, JpaSpecificationExecutor<EventTerm> {

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"registrations"})
    Optional<EventTerm> findLoadedById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EventTerm> findForUpdateById(Long id);
}
