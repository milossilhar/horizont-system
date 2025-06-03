package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.EmailLog;

@Repository
public interface EmailLogRepository extends ListCrudRepository<EmailLog, Long> { }
