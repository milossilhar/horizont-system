package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.*;
import sk.leziemevpezinku.spring.api.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.mapper.base.BasicMapper;
import sk.leziemevpezinku.spring.api.mapper.base.UpdatableMapper;
import sk.leziemevpezinku.spring.model.EnumerationItem;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EnumerationItemMapper extends
        BasicMapper<EnumerationItem, EnumerationItemDTO>,
        UpdatableMapper<EnumerationItem, EnumerationItemDTO> {

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "description", source = "description")
    void updateEntity(EnumerationItemDTO dto, @MappingTarget EnumerationItem entity);
}
