package sk.leziemevpezinku.spring.api;

import org.mapstruct.factory.Mappers;

public abstract class AbstractMapperTest<T> {

    protected final T mapper;

    protected AbstractMapperTest(Class<T> mapperClazz) {
        this.mapper = Mappers.getMapper(mapperClazz);
    }


}
