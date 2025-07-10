package sk.leziemevpezinku.spring.mapper;

import org.mapstruct.*;
import sk.leziemevpezinku.spring.api.dto.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.dto.EventConditionTypeDTO;
import sk.leziemevpezinku.spring.api.dto.PlaceDTO;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = EnumerationMapperInterceptor.class
)
public interface EnumerationMapper {

    // GET - MAPPING
    default EnumerationItemDTO toDTO(EnumerationItem item) {
        if (item == null) return null;

        if (EnumerationName.REG_PLACE.equals(item.getEnumName())) {
            return toPlaceDTO(item);
        } else if (EnumerationName.REG_EVENT_CONDITION_TYPE.equals(item.getEnumName())) {
            return toEventConditionTypeDTO(item);
        }

        return toEnumerationItemDTO(item);
    }
    default List<? extends EnumerationItemDTO> toDTOList(List<EnumerationItem> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(this::toDTO)
                .toList();
    }
    EnumerationItemDTO toEnumerationItemDTO(EnumerationItem item);
    PlaceDTO toPlaceDTO(EnumerationItem item);
    EventConditionTypeDTO toEventConditionTypeDTO(EnumerationItem item);

    // CREATE - MAPPING
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, qualifiedByName = {"create"})
    @SubclassMapping(target = EnumerationItem.class, source = PlaceDTO.class)
    @SubclassMapping(target = EnumerationItem.class, source = EventConditionTypeDTO.class)
    EnumerationItem createEntity(EnumerationItemDTO dto, EnumerationName enumName);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, qualifiedByName = {"create"})
    @Mapping(target = "enumName", constant = EnumerationName.Names.REG_PLACE)
    EnumerationItem createEntity(PlaceDTO placeDTO);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, qualifiedByName = {"create"})
    @Mapping(target = "enumName", constant = EnumerationName.Names.REG_EVENT_CONDITION_TYPE)
    EnumerationItem createEntity(EventConditionTypeDTO eventConditionTypeDTO);

    // UPDATE - MAPPING
    default void updateEntity(EnumerationItemDTO dto, EnumerationItem entity) {
        if (dto == null || entity == null) return;

        updateEnumerationItem(dto, entity);

        if (dto instanceof PlaceDTO) {
            updatePlace((PlaceDTO) dto, entity);
        } else if (dto instanceof EventConditionTypeDTO) {
            updateEventConditionType((EventConditionTypeDTO) dto, entity);
        }
    }

    @BeanMapping(ignoreByDefault = true, qualifiedByName = {"update"})
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "ordering", target = "ordering")
    void updateEnumerationItem(EnumerationItemDTO dto, @MappingTarget EnumerationItem entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    void updatePlace(PlaceDTO dto, @MappingTarget EnumerationItem entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "conditionOption", target = "conditionOption")
    void updateEventConditionType(EventConditionTypeDTO dto, @MappingTarget EnumerationItem entity);
}
