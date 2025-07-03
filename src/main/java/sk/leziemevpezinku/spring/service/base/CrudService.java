package sk.leziemevpezinku.spring.service.base;

/**
 * A service interface that defines the standard Create, Read, Update, and Delete (CRUD)
 * operations for managing entities. This interface serves as a unification of multiple
 * specialized service interfaces, enabling consistent CRUD functionality.
 * <p>
 * The interface is generic, allowing it to be adapted for various entity types, data
 * transfer objects (DTOs), and unique identifier types.
 *
 * @param <DTO> the type of the data transfer object representing the entity
 * @param <CREATE_DTO> the type of the data transfer object used for creating new entities
 * @param <UPDATE_DTO> the type of the data transfer object used for updating existing entities
 * @param <ID> the type of the unique identifier for the entities
 */
public interface CrudService<DTO, CREATE_DTO, UPDATE_DTO, ID> extends
        CreateService<DTO, CREATE_DTO>,
        ReadService<DTO, ID>,
        UpdateService<DTO, UPDATE_DTO>,
        DeleteService<ID> {
    // extends everything
}
