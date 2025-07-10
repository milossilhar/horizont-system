package sk.leziemevpezinku.spring.repo;

import org.springframework.data.domain.Sort;
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
    List<EnumerationItem> findByEnumNameOrderByOrderingAsc(EnumerationName enumName);

    /**
     * Retrieves a list of {@link EnumerationItem} objects that match the given enumeration names,
     * sorted by their ordering in ascending order.
     *
     * @param enumNames the list of enumeration names to search for
     * @return a list of {@link EnumerationItem} objects sorted by their `ordering` property in ascending order
     */
    List<EnumerationItem> findByEnumNameInOrderByOrderingAsc(List<EnumerationName> enumNames);

    /**
     * Finds single enumeration item for given name and code.
     * @param enumName enum name
     * @param code enum item code
     * @return single enum item or empty
     */
    Optional<EnumerationItem> findByEnumNameAndCode(EnumerationName enumName, String code);

    /**
     * Checks if an enumeration item exists for the given enumeration name and code.
     *
     * @param enumName the name of the enumeration, represented as a {@link EnumerationName} value
     * @param code the code of the enumeration item to check for existence
     * @return true if an enumeration item with the given enumeration name and code exists, false otherwise
     */
    boolean existsByEnumNameAndCode(EnumerationName enumName, String code);

    /**
     * Finds the first {@link EnumerationItem} that matches the given enumeration name, applying the specified sort order.
     *
     * @param enumName the name of the enumeration to search for
     * @param sort the sorting criteria to apply
     * @return an {@link Optional} containing the first matching {@link EnumerationItem}, or an empty {@link Optional} if no match is found
     */
    Optional<EnumerationItem> findFirstByEnumName(EnumerationName enumName, Sort sort);
}
