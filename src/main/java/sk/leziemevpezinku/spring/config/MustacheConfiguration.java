package sk.leziemevpezinku.spring.config;

import com.samskivert.mustache.Mustache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MustacheConfiguration {

    @Bean
    public Mustache.Compiler compiler() {
        return Mustache.compiler()
                .defaultValue("");
    }
}
