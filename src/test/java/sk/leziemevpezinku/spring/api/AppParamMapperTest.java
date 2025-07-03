package sk.leziemevpezinku.spring.api;

import org.junit.jupiter.api.Test;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.api.dto.AppParamDTO;
import sk.leziemevpezinku.spring.mapper.AppParamMapper;
import sk.leziemevpezinku.spring.model.AppParam;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class AppParamMapperTest extends AbstractMapperTest<AppParamMapper> {

    public AppParamMapperTest() {
        super(AppParamMapper.class);
    }

    @Test
    public void testToEntity() {
        var entity = mapper.createEntity(
                AppParamDTO.builder()
                        .name("name")
                        .value("value")
                        .build());

        assertEquals("name", entity.getName());
        assertEquals("value", entity.getValue());
    }

    @Test
    public void testToDTO() {
        var dto = mapper.toDTO(
                AppParam.builder()
                        .name("name")
                        .value("value")
                        .build());

        assertEquals("name", dto.getName());
        assertEquals("value", dto.getValue());
    }
}
