package sk.leziemevpezinku.spring.api.mapper.base;

import org.mapstruct.MappingTarget;

public interface UpdatableMapper<E, D> {

    void updateEntity(D dto, @MappingTarget E entity);
}
