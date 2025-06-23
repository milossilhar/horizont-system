package sk.leziemevpezinku.spring.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.api.EnumerationItemDTO;
import sk.leziemevpezinku.spring.model.*;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;
import sk.leziemevpezinku.spring.rest.model.GenericRequest;
import sk.leziemevpezinku.spring.rest.model.PageableResponse;
import sk.leziemevpezinku.spring.service.EnumerationService;
import sk.leziemevpezinku.spring.service.EventService;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.RegistrationService;

import java.util.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "public")
public class PublicController {

    private final EnumerationService enumerationService;
    private final RegistrationService registrationService;
    private final NotificationService notificationService;
    private final EventService eventService;

    @GetMapping("/enumeration")
    public Map<EnumerationName, List<EnumerationItemDTO>> getEnumeration() {
        List<EnumerationItemDTO> visibleEnumerations = enumerationService.getVisibleEnumerations();

        Map<EnumerationName, List<EnumerationItemDTO>> result = new HashMap<>();

        visibleEnumerations.forEach(item -> result
                .computeIfAbsent(item.getName(), en -> new ArrayList<>())
                .add(item));

        return result;
    }

    @JsonView(Views.EventPublic.class)
    @GetMapping(path = "/events/current")
    public List<Event> getEventsCurrent() {
        return eventService.getCurrentAndFuture();
    }

    @JsonView(Views.EventPublic.class)
    @GetMapping(path = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageableResponse<Event> getEventsPS(@Valid Pageable pageable) {
        Page<Event> events = eventService.getAll(pageable);
        return PageableResponse.of(events);
    }

    @JsonView(Views.Public.class)
    @GetMapping(path = "/registrations", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageableResponse<Registration> getRegistrations(@Valid Pageable pageable) {
        Page<Registration> registrations = registrationService.getAll(pageable);
        return PageableResponse.of(registrations);
    }

    @JsonView(Views.EventPublic.class)
    @GetMapping(path = "/events/{eventUUID}")
    public Event getEventByUUID(@PathVariable("eventUUID") @NotNull String uuid) {
        return eventService.getByUUID(uuid);
    }

    @JsonView(Views.Public.class)
    @PostMapping(path = "/registration/{eventTermId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Registration createRegistration(
            @PathVariable("eventTermId") @NotNull Long eventTermId,
            @RequestBody @Valid Registration registration) {

        Registration createdRegistration = registrationService.createRegistration(eventTermId, registration);

        notificationService.sendRegistrationConfirmedNotification(createdRegistration);

        return createdRegistration;
    }

    @JsonView(Views.Public.class)
    @PostMapping(path = "/registration/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Registration confirmRegistration(
            @RequestBody @Valid GenericRequest<String> jwtTokenRequest) {

        Registration confirmedRegistration = registrationService.confirmRegistration(jwtTokenRequest.getValue());

        notificationService.sendRegistrationConfirmedNotification(confirmedRegistration);

        return confirmedRegistration;
    }
}
