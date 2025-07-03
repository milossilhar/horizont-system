package sk.leziemevpezinku.spring.service;

import sk.leziemevpezinku.spring.api.dto.EventDTO;
import sk.leziemevpezinku.spring.service.base.CrudPageService;
import sk.leziemevpezinku.spring.service.base.UuidService;

/**
 * Service interface for operations related to event management. This interface extends
 * the UuidCrudService interface, providing CRUD operations for events and enabling
 * retrieval by UUID.
 * <p>
 * This service typically handles tasks such as creating, updating, retrieving, and deleting
 * event data. It operates on data transfer objects (DTOs) to ensure a separation of concerns
 * between the service layer and persistence layer.
 */
public interface EventService extends
        UuidService<EventDTO>,
        CrudPageService<EventDTO, EventDTO, EventDTO, Long> {
    // custom contract here
}
