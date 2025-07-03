package sk.leziemevpezinku.spring.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.leziemevpezinku.spring.api.dto.PeriodDTO;
import sk.leziemevpezinku.spring.mapper.PeriodMapper;
import sk.leziemevpezinku.spring.model.Period;
import sk.leziemevpezinku.spring.repo.PeriodRepository;
import sk.leziemevpezinku.spring.api.dto.GenericResponseDTO;
import sk.leziemevpezinku.spring.api.exception.CommonException;
import sk.leziemevpezinku.spring.api.enumeration.ErrorCode;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PeriodController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "period")
//@RolesAllowed("ADMIN")
public class PeriodController {

    public static final String URL = "/periods";

    private final PeriodRepository repository;
    private final PeriodMapper mapper;

    @GetMapping
    public List<PeriodDTO> getPeriods() {
        return mapper.toDTOList(repository.findAll());
    }

    @GetMapping(path = "/{id:\\d+}")
    public PeriodDTO getPeriod(@PathVariable("id") @NotNull Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(
                () -> CommonException.builder()
                        .errorCode(ErrorCode.MSG_NOT_FOUND_PERIOD)
                        .parameter("id", id)
                        .build()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PeriodDTO createPeriod(@Valid @RequestBody PeriodDTO dto) {
        Period saved = repository.save(mapper.createEntity(dto));
        return mapper.toDTO(saved);
    }

    @PutMapping(path = "/{id:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PeriodDTO updatePeriod(@PathVariable("id") @NotNull Long id, @Valid @RequestBody PeriodDTO dto) {
        Period saved = repository.findById(id).orElseThrow(
                () -> CommonException.builder()
                        .errorCode(ErrorCode.MSG_NOT_FOUND_PERIOD)
                        .parameter("id", id)
                        .build());

        mapper.updateEntity(dto, saved);
        repository.save(saved);

        return mapper.toDTO(saved);
    }

    @DeleteMapping(path = "/{id:\\d+}")
    public GenericResponseDTO<String> deletePeriod(@PathVariable("id") @NotNull Long id) {
        repository.deleteById(id);
        return GenericResponseDTO.of("Successfully deleted.");
    }
}
