package sk.leziemevpezinku.spring.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Log4j2
@Configuration
public class OAuth2TokenConfiguration {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            if (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) &&
                !OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                return;
            }

            Authentication principal = context.getPrincipal();

            if (principal.getPrincipal() instanceof User user) {
                log.info("Adding claims to JWT token for {}", user);
                context.getClaims()
                        .claim("name", user.getUsername())
                        .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .filter(auth -> auth.startsWith("ROLE_"))
                                .map(role -> role.substring(5))
                                .toList())
                        .claim("authorities", user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList());
            }
        };
    }
}
