package sk.leziemevpezinku.spring.api;

import org.junit.jupiter.api.Test;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.api.mapper.AppParamMapper;
import sk.leziemevpezinku.spring.model.AppParam;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class AppParamMapperTest extends AbstractMapperTest<AppParamMapper> {

    public AppParamMapperTest() {
        super(AppParamMapper.class);
    }

    @Test
    public void testToEntity() {
        var entity = mapper.toEntity(
                AppParamDTO.builder()
                        .name("name")
                        .value("value")
                        .build());

        assertEquals("name", entity.getName());
        assertEquals("value", entity.getValue());
    }

    @Test
    public void testToEntityList() {
        var list = List.of(
                AppParamDTO.builder().name("name1").value("value1").build(),
                AppParamDTO.builder().name("name2").value("value2").build()
        );

        var dtos = mapper.toEntityList(list);

        assertEquals(2, dtos.size());
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
