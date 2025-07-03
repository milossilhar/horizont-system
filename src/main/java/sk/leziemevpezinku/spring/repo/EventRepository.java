package sk.leziemevpezinku.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.repo.base.UuidRepositoryBase;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends UuidRepositoryBase<Event, Long>, JpaSpecificationExecutor<Event>, CustomEventRepository {

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"terms"})
    List<Event> findAll();

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"terms"})
    List<Event> findAll(Specification<Event> spec);

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"terms"})
    Page<Event> findAll(Specification<Event> spec, Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"terms"})
    Optional<Event> findLoadedByUuid(String uuid);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"terms"})
    Optional<Event> findLoadedById(Long id);
}
