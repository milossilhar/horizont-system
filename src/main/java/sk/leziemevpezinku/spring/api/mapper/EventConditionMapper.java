package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import sk.leziemevpezinku.spring.api.EventConditionDTO;
import sk.leziemevpezinku.spring.api.mapper.base.BaseMapper;
import sk.leziemevpezinku.spring.model.EventCondition;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface EventConditionMapper extends
        BaseMapper<EventCondition, EventConditionDTO, EventConditionDTO, EventConditionDTO> { }
