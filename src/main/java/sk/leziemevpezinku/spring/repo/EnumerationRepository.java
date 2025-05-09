package sk.leziemevpezinku.spring.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.enums.EnumerationItem;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnumerationRepository extends ListCrudRepository<EnumerationItem, Long> {

    /**
     * Finds all enumerations with given visible flag
     * @param visible visible flag to find
     * @return list of all visible or invisible enums
     */
    List<EnumerationItem> findByVisible(boolean visible, Sort sort);

    /**
     * Finds all enumerations for given enumeration name.
     * @param name enum name
     * @return list of all enumerations
     */
    List<EnumerationItem> findByName(EnumerationName name);

    /**
     * Finds single enumeration item for given name and code.
     * @param name enum name
     * @param code enum item code
     * @return single enum item or empty
     */
    Optional<EnumerationItem> findByNameAndCode(EnumerationName name, String code);
}
