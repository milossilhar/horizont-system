package sk.leziemevpezinku.spring.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.leziemevpezinku.spring.model.EventTerm;
import sk.leziemevpezinku.spring.model.Views;
import sk.leziemevpezinku.spring.service.EventTermService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/eventTerms", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "eventTerm")
@RolesAllowed("ADMIN")
public class EventTermController {

    private final EventTermService eventTermService;

    @GetMapping("/{id:\\d+}")
    @JsonView(Views.EventTerm.class)
    public EventTerm getEventTerm(@PathVariable("id") @NotNull Long eventTermId) {
        return eventTermService.getById(eventTermId);
    }

    @GetMapping("/current")
    @JsonView(Views.Public.class)
    public List<EventTerm> getEventTermsCurrent() {
        return eventTermService.getCurrent();
    }
}
