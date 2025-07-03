package sk.leziemevpezinku.spring.api.mapper.base;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import sk.leziemevpezinku.spring.api.mapper.annotation.IgnoreAuditedProperties;

import java.util.List;
import java.util.Set;

/**
 * A generic interface for mapping between different data representations, such as entities
 * and their corresponding data transfer objects (DTOs). This interface defines methods for
 * mapping single or collection objects and performing entity updates.
 *
 * @param <ENTITY> The type of the entity being mapped.
 * @param <DTO> The type of the primary data transfer object (DTO) used for representing the entity's data.
 * @param <CREATE_DTO> The type of the DTO used for creating new entities.
 * @param <UPDATE_DTO> The type of the DTO used for updating existing entities.
 */
public interface BaseMapper<ENTITY, DTO, CREATE_DTO, UPDATE_DTO> {
    DTO toDTO(ENTITY entity);

    List<DTO> toDTOList(List<ENTITY> entities);
    Set<DTO> toDTOSet(Set<ENTITY> entities);

    default Page<DTO> toDTOPage(Page<ENTITY> entities) {
        return entities.map(this::toDTO);
    }

    ENTITY createEntity(CREATE_DTO dto);

    @BeanMapping(ignoreByDefault = true)
    void updateEntity(UPDATE_DTO dto, @MappingTarget ENTITY entity);
}
