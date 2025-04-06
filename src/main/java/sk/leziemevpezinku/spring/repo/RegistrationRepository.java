package sk.leziemevpezinku.spring.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Registration;

import java.util.List;

@Repository
public interface RegistrationRepository extends ListCrudRepository<Registration, Long>, JpaSpecificationExecutor<Registration> {

    /**
     * Finds registrations by transaction ID attribute.
     * @param transactionId given transaction ID
     * @return found Registrations for given transaction ID
     */
    List<Registration> findByTransactionId(String transactionId);
}
