package sk.leziemevpezinku.spring.api.mapper.base;

public interface BasicMapper<ENTITY, DTO> extends CollectionMapper<ENTITY, DTO> {
    DTO toDTO(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
