package sk.leziemevpezinku.spring.api.mapper.base;

import java.util.List;
import java.util.Set;

public interface CollectionMapper<ENTITY, DTO> {
    List<DTO> toDTOList(List<ENTITY> entities);
    Set<DTO> toDTOSet(Set<ENTITY> entities);
    List<ENTITY> toEntityList(List<DTO> dtos);
    Set<ENTITY> toEntitySet(Set<DTO> dtos);
}
