package sk.leziemevpezinku.spring.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.model.Event;
import sk.leziemevpezinku.spring.model.Payment;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.model.Views;
import sk.leziemevpezinku.spring.model.enums.EnumerationItem;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;
import sk.leziemevpezinku.spring.repo.model.EventTermCapacity;
import sk.leziemevpezinku.spring.rest.model.EventTermCapacityResponse;
import sk.leziemevpezinku.spring.rest.model.GenericRequest;
import sk.leziemevpezinku.spring.rest.model.RegistrationPricingRequest;
import sk.leziemevpezinku.spring.service.EnumerationService;
import sk.leziemevpezinku.spring.service.EventService;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.RegistrationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "public")
public class PublicController {

    private final EnumerationService enumerationService;
    private final RegistrationService registrationService;
    private final NotificationService notificationService;
    private final EventService eventService;

    @JsonView(Views.Public.class)
    @GetMapping("/enums")
    public Map<EnumerationName, List<EnumerationItem>> getPublicEnumerations() {
        List<EnumerationItem> visibleEnumerations = enumerationService.getVisibleEnumerations();

        Map<EnumerationName, List<EnumerationItem>> result = new HashMap<>();

        visibleEnumerations.forEach(enumerationItem -> result
                .computeIfAbsent(enumerationItem.getName(), en -> new ArrayList<>())
                .add(enumerationItem));

        return result;
    }

    @JsonView(Views.EventPublic.class)
    @GetMapping(path = "/events/current")
    public List<Event> getEventsCurrent() {
        return eventService.getCurrentAndFuture();
    }

    @JsonView(Views.EventPublic.class)
    @GetMapping(path = "/events/{eventUUID}")
    public Event getEventByUUID(@PathVariable("eventUUID") @NotNull String uuid) {
        return eventService.getByUUID(uuid);
    }

    @JsonView(Views.Public.class)
    @GetMapping(path = "/events/{eventUUID}/capacity")
    public Map<Long, EventTermCapacityResponse> getEventCapacities(@PathVariable("eventUUID") @NotNull String eventUUID) {
        List<EventTermCapacity> eventRegistrationCount = eventService.getEventRegistrationCount(eventUUID);

        Map<Long, EventTermCapacityResponse> result = new HashMap<>();
        eventRegistrationCount.forEach(etc -> {
            result.computeIfAbsent(etc.getEventTermId(),
                    (key) -> new EventTermCapacityResponse(etc.getCapacity())
                            .addRegistered(etc.getRegisteredCount())
                            .addConfirmed(etc.getConfirmedCount())
            );

            result.computeIfPresent(etc.getEventTermId(),
                    (key, response) -> response
                            .addRegistered(etc.getRegisteredCount())
                            .addConfirmed(etc.getConfirmedCount())
            );
        });

        return result;
    }

    @JsonView(Views.Public.class)
    @PostMapping(path = "/registration/{eventTermId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Registration createRegistration(
            @PathVariable("eventTermId") @NotNull Long eventTermId,
            @RequestBody @Valid Registration registration) {

        Registration createdRegistration = registrationService.createRegistration(eventTermId, registration);

        notificationService.sendRegistrationCreatedNotification(createdRegistration);

        return createdRegistration;
    }

    @JsonView(Views.Public.class)
    @PostMapping(path = "/registration/{eventTermId:\\d+}/calculate-price", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Payment calculatePriceForRegistration(
            @PathVariable("eventTermId") @NotNull Long eventTermId,
            @RequestBody @Valid RegistrationPricingRequest request) {

        return registrationService.calculatePriceForRegistration(eventTermId, request.getUserEmail(), request.getNumberOfPeople());
    }

    @JsonView(Views.Public.class)
    @PostMapping(path = "/registration/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Registration confirmRegistration(
            @RequestBody @Valid GenericRequest<String> jwtTokenRequest) {

        Registration comfirmedRegistration = registrationService.confirmRegistration(jwtTokenRequest.getValue());

        notificationService.sendRegistrationConfirmedNotification(comfirmedRegistration);

        return comfirmedRegistration;
    }
}
