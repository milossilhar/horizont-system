package sk.leziemevpezinku.spring.service.base;

import java.util.List;

/**
 * Interface for services that provide the ability to retrieve a list of entities
 * by their common UUID. This interface is typically used when multiple entities
 * share a single UUID and need to be fetched collectively.
 *
 * @param <DTO> the type of the data transfer object used to represent the entities
 */
public interface UuidListService<DTO> {

    List<DTO> getAllByUuid(String uuid);
}
