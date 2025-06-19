package sk.leziemevpezinku.spring.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Registration;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>, JpaSpecificationExecutor<Registration>, CustomRegistrationRepository {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH)
    Optional<Registration> findUninitializedById(Long id);

    /**
     * Finds all registrations for given event term id.
     * @param eventTermId event term id
     * @return list of registrations
     */
    List<Registration> findByEventTermId(Long eventTermId);

    /**
     * Finds all registrations with same uuid.
     * @param uuid uuid
     * @return list of registrations
     */
    List<Registration> findByUUID(String uuid);

    /**
     * Finds registration for given payment id.
     * @param paymentId payment id
     * @return found registration, maybe
     */
    Optional<Registration> findByPaymentId(Long paymentId);
}
