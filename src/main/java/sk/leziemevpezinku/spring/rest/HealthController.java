package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.leziemevpezinku.spring.rest.model.GenericResponse;

@RestController
@RequestMapping(path = HealthController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "health")
public class HealthController {

    public static final String URL = "/health";

    @Value("${horizon.environment}")
    private String environment;

    @GetMapping(path = "/env")
    public GenericResponse<String> getEnvironment() {
        return GenericResponse.of(environment);
    }
}
