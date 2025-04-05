package sk.leziemevpezinku.spring.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.leziemevpezinku.spring.model.Registration;
import sk.leziemevpezinku.spring.model.User;
import sk.leziemevpezinku.spring.service.RegistrationService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping(
            path = "/{eventTermId:\\d+}"
    )
    public List<Registration> createRegistration(
            @PathVariable("eventTermId") @NotNull Long eventTermId,
            @RequestBody @Valid User user) {
        return registrationService.createRegistration(eventTermId, user);
    }
}
