package sk.leziemevpezinku.spring.mapper;

import org.mapstruct.*;
import sk.leziemevpezinku.spring.api.dto.PlaceDTO;
import sk.leziemevpezinku.spring.mapper.base.BaseMapper;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PlaceMapper extends
        BaseMapper<EnumerationItem, PlaceDTO, PlaceDTO, PlaceDTO> {

    @Override
    @Mapping(target = "enumName", constant = EnumerationName.Names.REG_PLACE)
    EnumerationItem createEntity(PlaceDTO placeDTO);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "ordering", source = "ordering")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "hidden", source = "hidden")
    void updateEntity(PlaceDTO dto, @MappingTarget EnumerationItem entity);
}
