package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Person;

@Repository
public interface PersonRepository extends ListCrudRepository<Person, Long> {
}
