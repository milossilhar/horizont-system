package sk.leziemevpezinku.spring.service.base;


import java.util.List;

public interface ReadService<DTO, ID> {

    /**
     * Retrieves a list of all entities and returns them as data transfer objects (DTOs).
     *
     * @return a list of all entities represented as DTOs
     */
    List<DTO> getAll();

    /**
     * Retrieves a single entity by its unique identifier (ID) and returns it as a data transfer object (DTO).
     *
     * @param id the unique identifier of the entity to be retrieved
     * @return the entity represented as a DTO
     */
    DTO getOne(ID id);
}
