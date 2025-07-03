package sk.leziemevpezinku.spring.service.base;

/**
 * A service interface for performing updates on entities.
 *
 * @param <DTO> the data transfer object representing the entity after update
 * @param <UPDATE_DTO> the data transfer object containing the data for the update
 */
public interface UpdateService<DTO, UPDATE_DTO> {

    /**
     * Updates an existing entity based on the provided update DTO and returns the updated entity as a DTO.
     *
     * @param updateDto the data transfer object containing the updated details of the entity
     * @return the updated entity represented as a DTO
     */
    DTO update(UPDATE_DTO updateDto);
}
