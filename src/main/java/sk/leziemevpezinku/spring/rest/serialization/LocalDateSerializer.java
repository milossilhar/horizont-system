package sk.leziemevpezinku.spring.rest.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate localDate, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        if (localDate == null) {
            serializers.defaultSerializeNull(generator);
            return;
        }

        generator.writeString(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
