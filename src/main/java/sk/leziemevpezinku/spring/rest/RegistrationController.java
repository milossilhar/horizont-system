package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.service.NotificationService;
import sk.leziemevpezinku.spring.service.PaymentService;
import sk.leziemevpezinku.spring.service.RegistrationService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/registrations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "registration")
@RolesAllowed("ADMIN")
public class RegistrationController {

    private final PaymentService paymentService;
    private final RegistrationService registrationService;
    private final NotificationService notificationService;

    @PostMapping("/confirm/{paymentId:\\d+}")
    public Registration confirmPayment(
            @PathVariable("paymentId") @NotNull Long paymentId,
            @RequestParam("deposit") @NotNull Boolean deposit) {
        BigDecimal paidAmount = paymentService.confirmPayment(paymentId, deposit);

        Registration registration = registrationService.getByPaymentId(paymentId);
        notificationService.sendPaymentConfirmationNotification(registration, deposit, paidAmount);
        return registration;
    }
}
