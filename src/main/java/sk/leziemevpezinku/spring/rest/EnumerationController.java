package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.api.dto.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.service.EnumerationService;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = EnumerationController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "enumeration")
//@RolesAllowed("ADMIN")
public class EnumerationController {

    public static final String URL = "/enumerations";

    private final EnumerationService service;

    @GetMapping("/administrated")
    List<String> getNames() {
        return EnumerationName.administrated().stream().map(Enum::name).toList();
    }

    @GetMapping("/name/{enumName}")
    List<? extends EnumerationItemDTO> getEnumItems(
            @PathVariable("enumName") @NotNull EnumerationName enumName
    ) {
        return service.getAll(enumName);
    }

    @PostMapping(value = "/name/{enumName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<? extends EnumerationItemDTO> createEnumItem(
            @PathVariable("enumName") @NotNull EnumerationName enumName,
            @RequestBody @Valid EnumerationItemDTO item
    ) {
        return service.create(enumName, item);
    }
}
