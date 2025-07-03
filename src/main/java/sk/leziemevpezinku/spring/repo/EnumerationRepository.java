package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnumerationRepository extends ListCrudRepository<EnumerationItem, Long> {

    /**
     * Finds all enumerations for a given enumeration name.
     * @param enumName enum name
     * @return list of all enumerations
     */
    List<EnumerationItem> findByEnumNameOrderByOrderingAsc(String enumName);

    /**
     * Finds single enumeration item for given name and code.
     * @param enumName enum name
     * @param code enum item code
     * @return single enum item or empty
     */
    Optional<EnumerationItem> findByEnumNameAndCode(String enumName, String code);

    /**
     * Checks if an enumeration item exists for the given enumeration name and code.
     *
     * @param enumName the name of the enumeration, represented as a {@link EnumerationName} value
     * @param code the code of the enumeration item to check for existence
     * @return true if an enumeration item with the given enumeration name and code exists, false otherwise
     */
    boolean existsByEnumNameAndCode(String enumName, String code);
}
