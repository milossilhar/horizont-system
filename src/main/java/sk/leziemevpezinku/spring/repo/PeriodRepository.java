package sk.leziemevpezinku.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Period;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
}
