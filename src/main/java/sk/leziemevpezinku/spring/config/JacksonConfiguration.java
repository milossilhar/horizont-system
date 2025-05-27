package sk.leziemevpezinku.spring.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.leziemevpezinku.spring.rest.mapper.LocalDateDeserializer;
import sk.leziemevpezinku.spring.rest.mapper.LocalDateSerializer;
import sk.leziemevpezinku.spring.rest.mapper.LocalDateTimeDeserializer;
import sk.leziemevpezinku.spring.rest.mapper.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomizer() {
        return builder -> {
            SimpleModule customDateModule = new SimpleModule();
            customDateModule.addSerializer(LocalDate.class, new LocalDateSerializer());
            customDateModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
            customDateModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            customDateModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

            builder.modulesToInstall(customDateModule);
        };
    }
}
