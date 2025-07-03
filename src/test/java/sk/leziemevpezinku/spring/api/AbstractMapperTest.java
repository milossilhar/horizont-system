package sk.leziemevpezinku.spring.api;

import org.junit.jupiter.api.TestInstance;
import org.mapstruct.factory.Mappers;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractMapperTest<T> {

    protected final T mapper;

    protected AbstractMapperTest(Class<T> mapperClazz) {
        this.mapper = Mappers.getMapper(mapperClazz);
    }


}
