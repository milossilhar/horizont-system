package sk.leziemevpezinku.spring.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import sk.leziemevpezinku.spring.api.AppParamDTO;
import sk.leziemevpezinku.spring.api.mapper.base.BasicMapper;
import sk.leziemevpezinku.spring.model.AppParam;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppParamMapper extends BasicMapper<AppParam, AppParamDTO> { }
