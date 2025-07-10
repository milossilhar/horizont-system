package sk.leziemevpezinku.spring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.leziemevpezinku.spring.api.dto.EnumerationItemDTO;
import sk.leziemevpezinku.spring.mapper.EnumerationMapper;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.model.EnumerationItem_;
import sk.leziemevpezinku.spring.repo.EnumerationRepository;
import sk.leziemevpezinku.spring.service.EnumerationService;
import sk.leziemevpezinku.spring.api.exception.CommonException;
import sk.leziemevpezinku.spring.api.enumeration.ErrorCode;

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
    private final EnumerationMapper mapper;

    @Override
    public EnumerationItemDTO create(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO) {
        var entity = mapper.createEntity(enumerationItemDTO, enumName);

        if (entity.getOrdering() == null) {
            var ordering = repository.findFirstByEnumName(enumName, Sort.by(Sort.Order.desc(EnumerationItem_.ORDERING)))
                    .map(i -> i.getOrdering() + 1)
                    .orElse(1);
            entity.setOrdering(ordering);
        }

        var saved = repository.save(entity);

        return mapper.toDTO(saved);
    }

    @Override
    public List<? extends EnumerationItemDTO> update(EnumerationName enumName, EnumerationItemDTO enumerationItemDTO) {
        var item = repository.findById(enumerationItemDTO.getId()).orElseThrow(
                () -> CommonException.builder()
                        .errorCode(ErrorCode.MSG_NOT_FOUND_ENUMERATION_ITEM)
                        .parameter("id", enumerationItemDTO.getId())
                        .build()
        );

        mapper.updateEntity(enumerationItemDTO, item);
        repository.save(item);

        return getAll(enumName);
    }

    @Override
    public List<? extends EnumerationItemDTO> hide(EnumerationName enumName, String code) {
        if (!enumName.isAdministrated()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ACCESS_DENIED)
                    .build();
        }

        var item = repository.findByEnumNameAndCode(enumName, code).orElseThrow(
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
        var enums = repository.findByEnumNameOrderByOrderingAsc(enumName);
        return mapper.toDTOList(enums);
    }

    @Override
    public Map<EnumerationName, List<? extends EnumerationItemDTO>> getAll(List<EnumerationName> enumNames) {
        var enums = repository.findByEnumNameInOrderByOrderingAsc(enumNames);

        return enums.stream()
                .collect(Collectors.groupingBy(EnumerationItem::getEnumName))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> mapper.toDTOList(e.getValue())));
    }

    @Override
    public String getName(EnumerationName enumName, String code) {
        Objects.requireNonNull(enumName, "enumName cannot be null");
        Objects.requireNonNull(code, "code cannot be null");

        return repository.findByEnumNameAndCode(enumName, code)
                .orElse(EnumerationItem.builder().name(null).build())
                .getName();
    }
}
