package sk.leziemevpezinku.spring.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Getter
@Setter
@Component
@ConfigurationProperties("cors")
public class WebCorsConfiguration implements CorsConfigurationSource {

    private String allowedOrigins;
    private String allowedMethods;
    private String allowedHeaders;

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.stream(allowedOrigins.split(",")).toList());
        config.setAllowedMethods(Arrays.stream(allowedMethods.split(",")).toList());
        config.setAllowedHeaders(Arrays.stream(allowedHeaders.split(",")).toList());
        return config;
    }
}
