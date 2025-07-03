package sk.leziemevpezinku.spring.rest.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import sk.leziemevpezinku.spring.util.DateUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Stream;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JacksonException {
        String dateText = parser.getText();
        if (dateText == null || dateText.trim().isEmpty()) {
            return null;
        }

        var localDate = Stream.of(DateTimeFormatter.ISO_OFFSET_DATE_TIME, DateTimeFormatter.ISO_DATE)
                .map(formatter -> DateUtils.tryParseDate(dateText, formatter))
                .filter(Objects::nonNull)
                .findFirst();

        if (localDate.isEmpty()) {
            throw new IOException("Unable to parse date: " + dateText);
        }

        return localDate.get();
    }
}
