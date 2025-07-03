package sk.leziemevpezinku.spring.service.base;

/**
 * Extension of the CrudService interface that provides additional functionality
 * for retrieving entities by their UUID.
 *
 * @param <DTO> the data transfer object used to represent the entity
 */
public interface UuidService<DTO> {

    /**
     * Retrieves a single entity by its UUID and returns it as a data transfer object (DTO).
     *
     * @param uuid the unique identifier of the entity to retrieve, represented as a UUID
     * @return the entity represented as a DTO, or null if no entity with the provided UUID is found
     */
    DTO getByUuid(String uuid);
}
