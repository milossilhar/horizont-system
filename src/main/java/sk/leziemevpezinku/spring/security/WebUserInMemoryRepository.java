package sk.leziemevpezinku.spring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebUserInMemoryRepository {

    @Value("${horizon.auth.admin.username}")
    private String adminUsername;

    @Value("${horizon.auth.admin.password}")
    private String adminPassword;

    @Bean
    public UserDetailsService createUserService() {
        var user = User.withDefaultPasswordEncoder()
                .username(adminUsername)
                .password(adminPassword)
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
