package sk.leziemevpezinku.spring.repo.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sk.leziemevpezinku.spring.model.base.UuidAuditedEntity;

import java.util.Optional;

@NoRepositoryBean
public interface UuidRepositoryBase<T extends UuidAuditedEntity, ID> extends JpaRepository<T, ID> {

    /**
     * Finds entity by uuid column.
     * @param uuid given uuid to find
     * @return Optional result either found or not
     */
    Optional<T> findByUuid(String uuid);
}
