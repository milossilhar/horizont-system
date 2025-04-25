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
@RequestMapping(path = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    private final NotificationService notificationService;

    @JsonView(Views.Public.class)
    @PostMapping(path = "/{eventTermId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Registration createRegistration(
            @PathVariable("eventTermId") @NotNull Long eventTermId,
            @RequestBody @Valid Registration registration) {

        Registration createdRegistration = registrationService.createRegistration(eventTermId, registration);

        notificationService.sendRegistrationCreatedNotification(createdRegistration);

        return createdRegistration;
    }

    @JsonView(Views.Public.class)
    @PostMapping(path = "/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Registration confirmRegistration(
            @RequestBody @Valid GenericRequest<String> jwtTokenRequest) {

        Registration comfirmedRegistration = registrationService.confirmRegistration(jwtTokenRequest.getValue());

        notificationService.sendRegistrationConfirmedNotification(comfirmedRegistration);

        return comfirmedRegistration;
    }

    @PostMapping(path = "/calculate-price", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Payment calculatePriceForRegistration(
            @RequestBody @Valid RegistrationPricingRequest request) {

        return registrationService.calculatePriceForRegistration(request.getEventTermId(), request.getUserEmail(), request.getNumberOfPeople());
    }
}
