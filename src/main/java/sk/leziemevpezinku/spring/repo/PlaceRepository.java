package sk.leziemevpezinku.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
