package sk.leziemevpezinku.spring.repo;

import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.repo.base.UidRepositoryBase;

@Repository
public interface EventRepository extends UidRepositoryBase<Event, Long> {

}
