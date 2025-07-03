package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.api.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.PlaceDTO;
import sk.leziemevpezinku.spring.api.mapper.EnumerationItemMapper;
import sk.leziemevpezinku.spring.api.mapper.PlaceMapper;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;
import sk.leziemevpezinku.spring.repo.EnumerationRepository;
import sk.leziemevpezinku.spring.service.EnumerationService;
import sk.leziemevpezinku.spring.service.exception.CommonException;
import sk.leziemevpezinku.spring.service.model.ErrorCode;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class EnumerationServiceBean implements EnumerationService {

    private final EnumerationRepository repository;

    // mappers
    private final EnumerationItemMapper enumerationItemMapper;
    private final PlaceMapper placeMapper;

    @Override
    public List<? extends EnumerationItemDTO> create(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO) {
        validate(enumName, enumerationItemDTO);
        checkDuplicate(enumName, enumerationItemDTO);

        var entity = createEntity(enumName, enumerationItemDTO);
        repository.save(entity);

        return getAll(enumName);
    }

    @Override
    public List<? extends EnumerationItemDTO> update(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO) {
        validate(enumName, enumerationItemDTO);
        Objects.requireNonNull(enumerationItemDTO.getId(), "Enumeration item id cannot be null");

        var item = repository.findById(enumerationItemDTO.getId()).orElseThrow(
                () -> CommonException.builder()
                        .errorCode(ErrorCode.MSG_NOT_FOUND_ENUMERATION_ITEM)
                        .parameter("id", enumerationItemDTO.getId())
                        .build()
        );

        updateEntity(enumName, enumerationItemDTO, item);
        repository.save(item);

        return getAll(enumName);
    }

    @Override
    public List<? extends EnumerationItemDTO> getAll(EnumerationName enumName) {
        var enums = repository.findByEnumNameOrderByOrderingAsc(enumName.name());
        return toDTOList(enumName, enums);
    }

    @Override
    public String getName(EnumerationName enumName, String code) {
        Objects.requireNonNull(enumName, "enumName cannot be null");
        Objects.requireNonNull(code, "code cannot be null");

        return repository.findByEnumNameAndCode(enumName.name(), code)
                .orElse(EnumerationItem.builder().name(null).build())
                .getName();
    }

    private void validate(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO) {
        Objects.requireNonNull(enumName, "enumName cannot be null");
        Objects.requireNonNull(enumerationItemDTO, "enumerationItemDTO cannot be null");

        if (enumName.isAdministrated()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ACCESS_DENIED)
                    .build();
        }

        if (!enumName.getDtoClass().isInstance(enumerationItemDTO)) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ENUM_TYPE_MISMATCH)
                    .build();
        }
    }

    private void checkDuplicate(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO) {
        if (repository.existsByEnumNameAndCode(enumName.name(), enumerationItemDTO.getCode())) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ENUM_DUPLICATE)
                    .parameter("enumName", enumName.name())
                    .parameter("code", enumerationItemDTO.getCode())
                    .build();
        }
    }

    private EnumerationItem createEntity(EnumerationName enumName, EnumerationItemDTO dto) {
        if (dto instanceof PlaceDTO placeDTO) {
            return placeMapper.createEntity(placeDTO);
        }

        return enumerationItemMapper.createEntity(dto, enumName);
    }

    private void updateEntity(EnumerationName enumName, EnumerationItemDTO dto, EnumerationItem entity) {
        if (dto instanceof PlaceDTO placeDTO) {
            placeMapper.updateEntity(placeDTO, entity);
        }

        enumerationItemMapper.updateEntity(dto, entity);
    }

    private List<? extends EnumerationItemDTO> toDTOList(EnumerationName enumName, List<EnumerationItem> items) {
        if (EnumerationName.REG_PLACE.equals(enumName)) {
            return placeMapper.toDTOList(items);
        }

        return enumerationItemMapper.toDTOList(items);
    }
}
