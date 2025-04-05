package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnumerationRepository extends CrudRepository<EnumerationItem, Long> {

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
