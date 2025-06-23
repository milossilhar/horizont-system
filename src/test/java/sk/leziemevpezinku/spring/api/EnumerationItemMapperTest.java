package sk.leziemevpezinku.spring.api;

import org.junit.jupiter.api.Test;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.api.mapper.EnumerationItemMapper;
import sk.leziemevpezinku.spring.model.EnumerationItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class EnumerationItemMapperTest extends AbstractMapperTest<EnumerationItemMapper> {

    protected EnumerationItemMapperTest() {
        super(EnumerationItemMapper.class);
    }

    @Test
    public void testUpdate() {
        var dto = EnumerationItemDTO.builder()
                .code("ABC")
                .description("abeceda")
                .build();

        var entity = EnumerationItem.builder()
                .name("ENUM_SUPER")
                .code("zoo")
                .description("zooooo")
                .build();

        mapper.updateEntity(dto, entity);

        assertEquals("ENUM_SUPER", entity.getName());
        assertEquals("zoo", entity.getCode());
        assertEquals("abeceda", entity.getDescription());
    }
}
