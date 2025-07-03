package sk.leziemevpezinku.spring.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A service interface for retrieving paginated data transfer objects (DTOs).
 * This interface defines a contract for services that support pagination
 * functionality for a collection of DTO entities.
 *
 * @param <DTO> the type of the data transfer object
 */
public interface ReadPageService<DTO> {

    /**
     * Retrieves a paginated list of DTO objects.
     *
     * @param pageable the pagination and sorting information
     * @return a paginated collection of DTO objects
     */
    Page<DTO> getPage(Pageable pageable);
}
