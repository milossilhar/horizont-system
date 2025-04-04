package sk.leziemevpezinku.spring.repo.base;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sk.leziemevpezinku.spring.model.base.UidAuditedEntityBase;

import java.util.Optional;

@NoRepositoryBean
public interface UidRepositoryBase<T extends UidAuditedEntityBase, ID> extends CrudRepository<T, ID> {

    /**
     * Finds entity by uuid column.
     * @param uuid given uuid to find
     * @return Optional result either found or not
     */
    Optional<T> findByUuid(String uuid);
}
