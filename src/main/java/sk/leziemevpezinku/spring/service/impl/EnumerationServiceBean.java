package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.api.dto.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.dto.PlaceDTO;
import sk.leziemevpezinku.spring.mapper.EnumerationItemMapper;
import sk.leziemevpezinku.spring.mapper.PlaceMapper;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.model.EnumerationItem_;
import sk.leziemevpezinku.spring.repo.EnumerationRepository;
import sk.leziemevpezinku.spring.service.EnumerationService;
import sk.leziemevpezinku.spring.api.exception.CommonException;
import sk.leziemevpezinku.spring.api.enumeration.ErrorCode;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public EnumerationItemDTO create(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO) {
        validate(enumName, enumerationItemDTO);
        checkDuplicate(enumName, enumerationItemDTO);

        var entity = createEntity(enumName, enumerationItemDTO);

        if (entity.getOrdering() == null) {
            var ordering = repository.findFirstByEnumName(enumName.name(), Sort.by(Sort.Order.desc(EnumerationItem_.ORDERING)))
                    .map(i -> i.getOrdering() + 1)
                    .orElse(1);
            entity.setOrdering(ordering);
        }

        var saved = repository.save(entity);

        return toDTO(saved);
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
    public List<? extends EnumerationItemDTO> hide(EnumerationName enumName, String code) {
        Objects.requireNonNull(enumName, "enumName cannot be null");
        if (!enumName.isAdministrated()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ACCESS_DENIED)
                    .build();
        }

        var item = repository.findByEnumNameAndCode(enumName.name(), code).orElseThrow(
                () -> CommonException.builder()
                        .errorCode(ErrorCode.MSG_NOT_FOUND_ENUMERATION_ITEM)
                        .parameter("enumName", enumName)
                        .parameter("code", code)
                        .build()
        );

        item.setHidden(true);
        repository.save(item);

        return getAll(enumName);
    }

    @Override
    public List<? extends EnumerationItemDTO> getAll(EnumerationName enumName) {
        var enums = repository.findByEnumNameOrderByOrderingAsc(enumName.name());
        return toDTOList(enumName, enums);
    }

    @Override
    public Map<String, List<? extends EnumerationItemDTO>> getAll(List<EnumerationName> enumNames) {
        var enums = repository.findByEnumNameInOrderByOrderingAsc(enumNames.stream().map(EnumerationName::name).toList());

        return enums.stream()
                .collect(Collectors.groupingBy(EnumerationItem::getEnumName))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> toDTOList(EnumerationName.valueOf(e.getKey()), e.getValue())));
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

        if (!enumName.isAdministrated()) {
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
        dto.setCode(StringUtils.randomSystemName(5, true));

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

    private EnumerationItemDTO toDTO(EnumerationItem item) {
        if (EnumerationName.REG_PLACE.name().equals(item.getEnumName())) {
            return placeMapper.toDTO(item);
        }
        return enumerationItemMapper.toDTO(item);
    }
}
