package sk.leziemevpezinku.spring.service.base;

import java.util.List;

/**
 * A service interface for fetching entities associated with a specific parent
 * identified by a unique identifier. This interface is useful for managing
 * relationships between parent and child entities and retrieving the associated
 * data transfer objects (DTOs) for child entities based on the parent entity.
 *
 * @param <DTO> the type of data transfer object returned by the service
 * @param <PARENT_ID> the type of unique identifier used for the parent entity
 */
public interface ReadByParentService<DTO, PARENT_ID> {

    /**
     * Retrieves a list of entities associated with a given parent identifier and returns them as data transfer objects (DTOs).
     *
     * @param parentId the unique identifier of the parent entity
     * @return a list of entities corresponding to the specified parent, represented as DTOs
     */
    List<DTO> getAllByParent(PARENT_ID parentId);
}
