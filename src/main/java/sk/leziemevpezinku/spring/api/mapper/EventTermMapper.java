package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.*;
import sk.leziemevpezinku.spring.api.EventTermDTO;
import sk.leziemevpezinku.spring.api.mapper.base.BaseMapper;
import sk.leziemevpezinku.spring.api.mapper.config.IgnoreUnmappedConfig;
import sk.leziemevpezinku.spring.model.EventTerm;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        config = IgnoreUnmappedConfig.class
)
public interface EventTermMapper extends
        BaseMapper<EventTerm, EventTermDTO, EventTermDTO, EventTermDTO> {

    @Override
    @Mapping(target = "eventId", source = "event.id")
    EventTermDTO toDTO(EventTerm eventTerm);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "repeatType", source = "repeatType")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "durationMinutes", source = "durationMinutes")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    @Mapping(target = "expectedTrainers", source = "expectedTrainers")
    @Mapping(target = "deposit", source = "deposit")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "capacity", source = "capacity")
    void updateEntity(EventTermDTO eventTermDTO, @MappingTarget EventTerm eventTerm);
}
