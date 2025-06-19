package sk.leziemevpezinku.spring.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

public class DateUtils {

    private static final String YY_MM_DD = "yyMMdd";
    private static final String COMPACT_FORMAT = "yyyyMMdd";
    private static final String PRINT_DATE_FORMAT = "dd.MM.yyyy";
    private static final String PRINT_DATE_TIME_FORMAT = "dd.MM.yyyy, HH:mm";

    /**
     * Formats datetime using YYMMDD (no spaces or characters as delimiters)
     * @param localDateTime datetime
     * @return formated datetime
     */
    public static String formatYYMMDD(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime);
        return formatter(YY_MM_DD).format(localDateTime);
    }

    /**
     * Formats date using YYMMDD (no spaces or characters as delimiters)
     * @param localDate date
     * @return formated date
     */
    public static String formatYYMMDD(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return formatter(YY_MM_DD).format(localDate);
    }

    /**
     * Formats datetime using YYYYMMDD (no spaces or characters as delimiters)
     * @param localDateTime datetime
     * @return formated datetime
     */
    public static String formatCompact(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime);
        return formatter(COMPACT_FORMAT).format(localDateTime);
    }

    /**
     * Formats date using YYYYMMDD (no spaces or characters as delimiters)
     * @param localDate date
     * @return formated date
     */
    public static String formatCompact(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return formatter(COMPACT_FORMAT).format(localDate);
    }

    public static String format(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime);
        return formatter(PRINT_DATE_TIME_FORMAT).format(localDateTime);
    }

    public static String format(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return formatter(PRINT_DATE_FORMAT).format(localDate);
    }

    public static String formatISO(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime);
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(toUTC(localDateTime));
    }

    public static String formatISO(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        return DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
    }

    public static ZonedDateTime tryParse(String text, DateTimeFormatter formatter) {
        try {
            return ZonedDateTime.parse(text, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static DateTimeFormatter formatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
    }

    private static LocalDateTime toUTC(LocalDateTime localDateTime) {
        return ZonedDateTime
                .of(localDateTime.truncatedTo(ChronoUnit.MILLIS), ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
    }
}
