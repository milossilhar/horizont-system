package sk.leziemevpezinku.spring.repo;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.repo.base.UidRepositoryBase;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends UidRepositoryBase<Event, Long>, JpaSpecificationExecutor<Event>, CustomEventRepository {

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"terms"})
    List<Event> findAll();

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"terms"})
    List<Event> findAll(Specification<Event> spec);

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"terms"})
    Optional<Event> findByUuid(String uuid);
}
