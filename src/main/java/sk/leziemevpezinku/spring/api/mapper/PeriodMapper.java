package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.*;
import sk.leziemevpezinku.spring.api.dto.PeriodDTO;
import sk.leziemevpezinku.spring.api.mapper.annotation.IgnoreAuditedProperties;
import sk.leziemevpezinku.spring.api.mapper.base.BaseMapper;
import sk.leziemevpezinku.spring.model.Period;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PeriodMapper extends
        BaseMapper<Period, PeriodDTO, PeriodDTO, PeriodDTO> {

    @Override
    @IgnoreAuditedProperties
    @Mapping(target = "events", ignore = true)
    Period createEntity(PeriodDTO periodDTO);

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    void updateEntity(PeriodDTO dto, @MappingTarget Period entity);
}
