package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.api.dto.EventTermDTO;
import sk.leziemevpezinku.spring.service.base.CrudService;
import sk.leziemevpezinku.spring.service.base.ReadByParentService;

/**
 * Service interface for operations related to managing event terms. This interface combines
 * the capabilities of CRUD operations and the ability to retrieve event terms associated with
 * a parent entity.
 *
 * It extends the following interfaces:
 * - ReadByParentService for fetching event terms associated with a specific parent entity.
 * - CrudService for performing Create, Read, Update, and Delete (CRUD) operations on event terms.
 *
 * This service typically handles tasks such as creating new event terms, updating their details,
 * retrieving event terms individually or in bulk, and associating them with parent entities.
 */
public interface EventTermService extends
        ReadByParentService<EventTermDTO, Long>,
        CrudService<EventTermDTO, EventTermDTO, EventTermDTO, Long> {
    // custom contract here
}
