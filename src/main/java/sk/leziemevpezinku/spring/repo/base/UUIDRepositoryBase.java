package sk.leziemevpezinku.spring.repo.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sk.leziemevpezinku.spring.model.base.UUIDAuditedEntityBase;

import java.util.Optional;

@NoRepositoryBean
public interface UUIDRepositoryBase<T extends UUIDAuditedEntityBase, ID> extends JpaRepository<T, ID> {

    /**
     * Finds entity by uuid column.
     * @param uuid given uuid to find
     * @return Optional result either found or not
     */
    Optional<T> findByUuid(String uuid);
}
