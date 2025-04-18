package sk.leziemevpezinku.spring.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.model.Payment;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.model.Views;
import sk.leziemevpezinku.spring.rest.model.GenericRequest;
import sk.leziemevpezinku.spring.rest.model.RegistrationPricingRequest;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.RegistrationService;

@RestController
@RequestMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    private final NotificationService notificationService;

    @JsonView(Views.Public.class)
    @PostMapping(path = "/{eventTermId:\\d+}")
    public Registration createRegistration(
            @PathVariable("eventTermId") @NotNull Long eventTermId,
            @RequestBody @Valid Registration registration) {
        Registration createdRegistration = registrationService.createRegistration(eventTermId, registration);

        notificationService.sendRegistrationCreatedNotification(createdRegistration);

        return createdRegistration;
    }

    @JsonView(Views.Public.class)
    @PostMapping(path = "/confirm")
    public Registration confirmRegistration(
            @RequestBody @Valid GenericRequest<String> jwtTokenRequest) {
        return registrationService.confirmRegistration(jwtTokenRequest.getValue());
    }

    @PostMapping(path = "/calculate-price")
    public Payment calculatePriceForRegistration(
            @RequestBody @Valid RegistrationPricingRequest request) {
        return registrationService.calculatePriceForRegistration(request.getEventTermId(), request.getUserEmail(), request.getNumberOfPeople());
    }
}
