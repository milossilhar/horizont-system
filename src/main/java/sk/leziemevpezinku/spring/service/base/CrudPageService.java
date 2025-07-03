package sk.leziemevpezinku.spring.service.base;

/**
 * A service interface that provides CRUD operations along with paginated retrieval of data transfer objects (DTOs).
 * It combines the functionality from {@link ReadPageService} for pagination support and {@link CrudService}
 * for create, read, update, and delete operations.
 *
 * @param <DTO> the type of the data transfer object returned by the service
 * @param <CREATE_DTO> the type of the data transfer object used for creating new entities
 * @param <UPDATE_DTO> the type of the data transfer object used for updating existing entities
 * @param <ID> the type of the unique identifier for entities managed by the service
 */
public interface CrudPageService<DTO, CREATE_DTO, UPDATE_DTO, ID> extends
        ReadPageService<DTO>,
        CrudService<DTO, CREATE_DTO, UPDATE_DTO, ID> {
    // extends everything
}
