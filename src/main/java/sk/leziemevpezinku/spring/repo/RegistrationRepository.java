package sk.leziemevpezinku.spring.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.repo.base.UidRepositoryBase;

import java.util.List;

@Repository
public interface RegistrationRepository extends UidRepositoryBase<Registration, Long>, JpaSpecificationExecutor<Registration>, CustomRegistrationRepository {

    /**
     * Finds all registrations for given event term id.
     * @param eventTermId event term id
     * @return list of registrations
     */
    List<Registration> findByEventTermId(Long eventTermId);
}
