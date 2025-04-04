package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Event;

@Repository
public interface EventRepository extends ListCrudRepository<Event, Long> {
}
