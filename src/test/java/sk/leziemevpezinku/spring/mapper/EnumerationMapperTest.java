package sk.leziemevpezinku.spring.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sk.leziemevpezinku.spring.annotation.UnitTest;
import sk.leziemevpezinku.spring.api.dto.EnumerationItemDTO;
import sk.leziemevpezinku.spring.api.enumeration.EnumerationName;
import sk.leziemevpezinku.spring.model.EnumerationItem;
import sk.leziemevpezinku.spring.repo.EnumerationRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
public class EnumerationMapperTest {

    @Mock
    private EnumerationRepository repository;

    @InjectMocks
    private EnumerationMapperInterceptor enumerationMapperInterceptor;

    private EnumerationMapperImpl mapper;

    @BeforeEach
    public void setup() {
        this.mapper = new EnumerationMapperImpl(enumerationMapperInterceptor);
    }

    @Test
    public void testUpdate() {
        var dto = EnumerationItemDTO.builder()
                .code("ABC")
                .name("XYZ")
                .description("abeceda")
                .build();

        var entity = EnumerationItem.builder()
                .enumName(EnumerationName.REG_EVENT_TERM_TAG)
                .code("BEGIN")
                .name("Beginners")
                .description("zooooo")
                .build();

        mapper.updateEntity(dto, entity);

        assertEquals("BEGIN", entity.getCode());
        assertEquals("XYZ", entity.getName());
        assertEquals("abeceda", entity.getDescription());
    }
}
