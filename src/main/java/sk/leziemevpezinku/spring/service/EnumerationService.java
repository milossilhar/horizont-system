package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.api.EnumerationItemDTO;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;

import java.util.List;

public interface EnumerationService {

    /**
     * Creates a new enumeration item for a specified enumeration name.
     *
     * @param enumName the name of the enumeration to which a new item will be added
     * @param enumerationItemDTO the data transfer object containing the details of the enumeration item to be created
     * @return the created enumeration item as an EnumerationItemDTO
     */
    List<? extends EnumerationItemDTO> create(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO);

    /**
     * Updates an existing enumeration item in the specified enumeration.
     *
     * @param enumName the name of the enumeration containing the item to update
     * @param enumerationItemDTO the data transfer object containing the updated details of the enumeration item
     * @return a list of updated EnumerationItemDTO objects representing the items in the specified enumeration
     */
    List<? extends EnumerationItemDTO> update(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO);

    /**
     * Retrieves all enumeration items for a specified enumeration name.
     *
     * @param enumName the name of the enumeration for which to retrieve all items
     * @return a list of EnumerationItemDTO objects representing the items in the specified enumeration
     */
    List<? extends EnumerationItemDTO> getAll(EnumerationName enumName);

    /**
     * Retrieves the name of an enumeration item associated with a specific code
     * and enumeration name.
     *
     * @param enumName the name of the enumeration to search within
     * @param code the unique code identifying the specific enumeration item
     * @return the name of the enumeration item associated with the given code,
     *         or null if no match is found
     */
    String getName(EnumerationName enumName, String code);
}
