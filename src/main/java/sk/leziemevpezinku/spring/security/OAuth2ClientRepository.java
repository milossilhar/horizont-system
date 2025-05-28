package sk.leziemevpezinku.spring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@Configuration
public class OAuth2ClientRepository {

    @Value("${horizon.web.hostname}")
    private String hostname;

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        return new InMemoryRegisteredClientRepository(
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("horizon-web")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .authorizationGrantTypes(types -> {
                            types.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                            types.add(AuthorizationGrantType.REFRESH_TOKEN);
                        })
                        .redirectUri(hostname + "/index.html")
                        .redirectUri(hostname + "/silent-refresh.html")
                        .postLogoutRedirectUri(hostname + "/index.html")
                        .scopes(scopes -> {
                            scopes.add(OidcScopes.OPENID);
                            scopes.add(OidcScopes.PROFILE);
                            scopes.add(OidcScopes.EMAIL);
                        })
                        .clientSettings(ClientSettings.builder()
                                .requireAuthorizationConsent(false)
                                .requireProofKey(true)
                                .build())
                        .build()
        );
    }
}
