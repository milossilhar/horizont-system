package sk.leziemevpezinku.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class RegistrationSystemApplication {

    /**
     * Main function that runs Spring Application.
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RegistrationSystemApplication.class, args);
    }

}
