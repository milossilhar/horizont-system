package sk.leziemevpezinku.spring.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

@Log4j2
@Getter
@Setter
@Configuration
@ConfigurationProperties("horizon")
public class JVMConfiguration {

    private final static Locale DEFAULT_LOCALE = Locale.US;
    private final static TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("Europe/Bratislava");

    private String locale;
    private String timezone;

    @PostConstruct
    public void configureJVM() {
        String normalizedLocaleName = locale != null ? locale.replaceAll("_", "-") : "";
        Optional<Locale> foundLocale = Locale.availableLocales()
                .filter(l -> l.toLanguageTag().equalsIgnoreCase(normalizedLocaleName))
                .findFirst();

        if (foundLocale.isEmpty()) {
            log.warn("Cannot find locale with name: {} USING default locale {}", locale, DEFAULT_LOCALE);
            Locale.setDefault(DEFAULT_LOCALE);
        } else {
            log.info("Setting default LOCALE to {}", foundLocale.get());
            Locale.setDefault(foundLocale.get());
        }


        Optional<String> foundTimezoneID = Arrays.stream(TimeZone.getAvailableIDs())
                .filter(t -> t.equalsIgnoreCase(timezone))
                .findFirst();

        if (foundTimezoneID.isEmpty()) {
            log.warn("Cannot find timezone with name: {} USING default timezone {}", timezone, DEFAULT_TIMEZONE);
            TimeZone.setDefault(DEFAULT_TIMEZONE);
        } else {
            log.info("Setting default TIMEZONE to  {}", foundTimezoneID.get());
            TimeZone.setDefault(TimeZone.getTimeZone(foundTimezoneID.get()));
        }
    }
}
