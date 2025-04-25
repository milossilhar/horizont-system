package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.leziemevpezinku.spring.model.enums.EnumerationItem;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;
import sk.leziemevpezinku.spring.service.EnumerationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/enumerations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "enumeration")
@RequiredArgsConstructor
public class EnumerationController {

    private final EnumerationService enumerationService;

    @GetMapping("/visible")
    public Map<EnumerationName, List<EnumerationItem>> getVisibleEnumerations() {
        List<EnumerationItem> visibleEnumerations = enumerationService.getVisibleEnumerations();

        Map<EnumerationName, List<EnumerationItem>> result = new HashMap<>();

        visibleEnumerations.forEach(enumerationItem -> result
                .computeIfAbsent(enumerationItem.getName(), en -> new ArrayList<>())
                .add(enumerationItem));

        return result;
    }
}
