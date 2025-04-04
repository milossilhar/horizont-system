package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Registration;

import java.util.List;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Long> {

    /**
     * Finds registrations by transaction ID attribute.
     * @param transactionId given transaction ID
     * @return found Registrations for given transaction ID
     */
    List<Registration> findByTransactionId(String transactionId);
}
