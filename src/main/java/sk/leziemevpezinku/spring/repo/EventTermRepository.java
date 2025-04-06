package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.EventTerm;

@Repository
public interface EventTermRepository extends ListCrudRepository<EventTerm, Long> {

}
