package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.*;
import sk.leziemevpezinku.spring.api.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.mapper.base.BaseMapper;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.model.enums.EnumerationName;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface EnumerationItemMapper extends
        BaseMapper<EnumerationItem, EnumerationItemDTO, EnumerationItemDTO, EnumerationItemDTO> {

    @Override
    @Mapping(target = "enumName", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    EnumerationItem createEntity(EnumerationItemDTO itemDTO);

    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    EnumerationItem createEntity(EnumerationItemDTO itemDTO, EnumerationName enumName);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "ordering", source = "ordering")
    @Mapping(target = "hidden", source = "hidden")
    void updateEntity(EnumerationItemDTO dto, @MappingTarget EnumerationItem entity);
}
