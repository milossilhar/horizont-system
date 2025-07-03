package sk.leziemevpezinku.spring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(
        info = @Info(
                title = "Registration System API",
                version = "1.0.0"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development server")
        }
)
public class RegistrationSystemApplication {

    /**
     * Main function that runs Spring Application.
     * @param args arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RegistrationSystemApplication.class, args);
    }

}
