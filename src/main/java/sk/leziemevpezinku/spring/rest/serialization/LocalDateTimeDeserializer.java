package sk.leziemevpezinku.spring.rest.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import sk.leziemevpezinku.spring.util.DateUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JacksonException {
        String dateText = parser.getText();
        if (dateText == null || dateText.trim().isEmpty()) {
            return null;
        }

        Optional<ZonedDateTime> zonedDateTime = Stream.of(DateTimeFormatter.ISO_OFFSET_DATE_TIME, DateTimeFormatter.ISO_DATE)
                .map(formatter -> DateUtils.tryParseDateTime(dateText, formatter))
                .filter(Objects::nonNull)
                .findFirst();

        if (zonedDateTime.isEmpty()) {
            throw new IOException("Unable to parse date: " + dateText);
        }

        return zonedDateTime.get()
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
