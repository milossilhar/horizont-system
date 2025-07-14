package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.api.dto.EnumerationDTO;
import sk.leziemevpezinku.spring.api.dto.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.service.EnumerationService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = EnumerationController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "enumeration")
//@RolesAllowed("ADMIN")
public class EnumerationController {

    public static final String URL = "/enumerations";

    private final EnumerationService service;

    @GetMapping()
    Map<EnumerationName, EnumerationDTO> getEnumeration() {
        Map<EnumerationName, List<? extends EnumerationItemDTO>> enumerationItems = service.getAll(List.of(EnumerationName.values()));

        return Arrays.stream(EnumerationName.values())
                .collect(
                        Collectors.toMap(
                                e -> e,
                                e -> EnumerationDTO.builder()
                                        .administrated(e.isAdministrated())
                                        .values(enumerationItems.getOrDefault(e, Collections.emptyList()))
                                        .build()
                        )
                );
    }

    @GetMapping("/items/administrated")
    Map<EnumerationName, List<? extends EnumerationItemDTO>> getItemsAdministrated() {
        return service.getAll(List.of(EnumerationName.values()));
    }

    @GetMapping("/items/name/{enumName}")
    List<? extends EnumerationItemDTO> getEnumItems(
            @PathVariable("enumName") @NotNull EnumerationName enumName
    ) {
        return service.getAll(enumName);
    }

    @PostMapping(value = "/{enumName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    EnumerationItemDTO createEnumItem(
            @PathVariable("enumName") @NotNull EnumerationName enumName,
            @RequestBody @Valid EnumerationItemDTO item
    ) {
        return service.create(enumName, item);
    }

    @DeleteMapping(value = "/{enumName}/{enumCode}")
    List<? extends EnumerationItemDTO> deleteEnumItem(
            @PathVariable("enumName") @NotNull EnumerationName enumName,
            @PathVariable("enumCode") @NotNull String enumCode
    ) {
        // only "SOFT" delete, cause of usage
        return service.hide(enumName, enumCode);
    }
}
