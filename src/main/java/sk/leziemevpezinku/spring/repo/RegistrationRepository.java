package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Registration;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Long> {

    /**
     * Finds registration by uid attribute.
     * @param uuid given uid
     * @return found Registration, or nothing (optional)
     */
    Optional<Registration> findByUuid(String uuid);
}
