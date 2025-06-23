package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import sk.leziemevpezinku.spring.api.EventTermDTO;
import sk.leziemevpezinku.spring.model.EventTerm;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventTermMapper {

    @Mapping(source = "registered", target = "registered")
    @Mapping(source = "eventTerm.durationMinutes", target = "duration")
    EventTermDTO toDTO(EventTerm eventTerm, Integer registered);
}
