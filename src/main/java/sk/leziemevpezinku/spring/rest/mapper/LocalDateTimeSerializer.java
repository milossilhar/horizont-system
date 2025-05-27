package sk.leziemevpezinku.spring.rest.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        if (localDateTime == null) {
            serializers.defaultSerializeNull(generator);
            return;
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        Instant instant = zonedDateTime
                .withZoneSameInstant(ZoneOffset.UTC)
                .toInstant()
                .truncatedTo(ChronoUnit.MILLIS);

        generator.writeString(DateTimeFormatter.ISO_INSTANT.format(instant));
    }
}
