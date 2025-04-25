package sk.leziemevpezinku.spring;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
public class RegistrationSystemApplication {

    @Value("${application.timezone:UTC}")
    private String applicationTimeZone;

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone));
    }

    /**
     * Main function that runs Spring Application.
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RegistrationSystemApplication.class, args);
    }

}
