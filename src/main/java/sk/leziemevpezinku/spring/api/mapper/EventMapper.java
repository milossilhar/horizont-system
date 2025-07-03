package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.*;
import sk.leziemevpezinku.spring.api.dto.EventDTO;
import sk.leziemevpezinku.spring.api.mapper.base.BaseMapper;
import sk.leziemevpezinku.spring.model.Event;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { EventConditionMapper.class }
)
public interface EventMapper extends
        BaseMapper<Event, EventDTO, EventDTO, EventDTO> {

    @Override
    @Mapping(target = "periodId", source = "period.id")
    EventDTO toDTO(Event event);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "registrationStarts", source = "registrationStarts")
    @Mapping(target = "registrationEnds", source = "registrationEnds")
    @Mapping(target = "placeCode", source = "placeCode")
    @Mapping(target = "period.id", source = "periodId")
    @Mapping(target = "conditions", source = "conditions")
    Event createEntity(EventDTO eventDTO);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "eventType", source = "eventType")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "registrationStarts", source = "registrationStarts")
    @Mapping(target = "registrationEnds", source = "registrationEnds")
    @Mapping(target = "placeCode", source = "placeCode")
    @Mapping(target = "period.id", source = "periodId")
    @Mapping(target = "conditions", source = "conditions")
    void updateEntity(EventDTO eventUpdateDTO, @MappingTarget Event event);
}
