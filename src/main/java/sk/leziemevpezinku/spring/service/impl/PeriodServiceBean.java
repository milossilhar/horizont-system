package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import sk.leziemevpezinku.spring.api.dto.PeriodDTO;
import sk.leziemevpezinku.spring.mapper.PeriodMapper;
import sk.leziemevpezinku.spring.model.Period;
import sk.leziemevpezinku.spring.repo.PeriodRepository;
import sk.leziemevpezinku.spring.service.PeriodService;
import sk.leziemevpezinku.spring.api.exception.CommonException;
import sk.leziemevpezinku.spring.api.enumeration.ErrorCode;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class PeriodServiceBean implements PeriodService {

    private final PeriodMapper mapper;
    private final PeriodRepository repository;

    @Override
    public PeriodDTO create(PeriodDTO periodDTO) {
        var period = mapper.createEntity(periodDTO);
        var saved = repository.save(period);
        return mapper.toDTO(saved);
    }

    @Override
    public List<PeriodDTO> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public PeriodDTO getOne(Long id) {
        var period = this.findPeriod(id);
        return mapper.toDTO(period);
    }

    @Override
    public PeriodDTO update(PeriodDTO periodDTO) {
        Objects.requireNonNull(periodDTO.getId(), "Period id cannot be null");

        var period = this.findPeriod(periodDTO.getId());
        mapper.updateEntity(periodDTO, period);
        var saved = repository.save(period);
        return mapper.toDTO(saved);
    }

    private Period findPeriod(Long id) {
        return repository.findById(id).orElseThrow(() -> CommonException.builder()
                .errorCode(ErrorCode.MSG_NOT_FOUND_PERIOD)
                .parameter("id", id)
                .build());
    }
}
