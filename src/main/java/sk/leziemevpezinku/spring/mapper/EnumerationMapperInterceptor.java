package sk.leziemevpezinku.spring.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import sk.leziemevpezinku.spring.api.dto.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.enumeration.ErrorCode;
import sk.leziemevpezinku.spring.api.exception.CommonException;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.repo.EnumerationRepository;
import sk.leziemevpezinku.spring.util.StringUtils;

@Getter
@Component
@RequiredArgsConstructor
public class EnumerationMapperInterceptor {

    private final EnumerationRepository repository;

    @BeforeMapping
    @Named("create")
    void beforeMappingCreate(EnumerationItemDTO dto) {
        if (dto == null) return;

        dto.setId(null);
        if (dto.getCode() == null) {
            dto.setCode(StringUtils.randomSystemName(5, true));
        }
    }

    @AfterMapping
    @Named("create")
    void afterMappingCreate(EnumerationItemDTO dto, @MappingTarget EnumerationItem entity) {
        if (dto == null || entity == null) return;

        if (!entity.getEnumName().getDtoClass().isInstance(dto)) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ENUM_TYPE_MISMATCH)
                    .build();
        }

        if (!entity.getEnumName().isAdministrated()) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ACCESS_DENIED)
                    .build();
        }

        if (repository.existsByEnumNameAndCode(entity.getEnumName(), entity.getCode())) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ENUM_DUPLICATE)
                    .parameter("enumName", entity.getEnumName())
                    .parameter("code", entity.getCode())
                    .build();
        }
    }

    @AfterMapping
    @Named("update")
    void afterMappingUpdate(EnumerationItemDTO dto, @MappingTarget EnumerationItem entity) {
        if (dto == null || entity == null) return;

        if (!entity.getEnumName().getDtoClass().isInstance(dto)) {
            throw CommonException.builder()
                    .errorCode(ErrorCode.MSG_ENUM_TYPE_MISMATCH)
                    .build();
        }
    }
}
