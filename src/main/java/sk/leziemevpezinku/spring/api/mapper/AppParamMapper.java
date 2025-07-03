package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.*;
import sk.leziemevpezinku.spring.api.dto.AppParamDTO;
import sk.leziemevpezinku.spring.api.mapper.base.BaseMapper;
import sk.leziemevpezinku.spring.model.AppParam;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AppParamMapper extends
        BaseMapper<AppParam, AppParamDTO, AppParamDTO, AppParamDTO> {

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "value", source = "value")
    void updateEntity(AppParamDTO dto, @MappingTarget AppParam appParam);
}
