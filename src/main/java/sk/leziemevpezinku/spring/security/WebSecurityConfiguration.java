package sk.leziemevpezinku.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.util.function.Function;

@Log4j2
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final WebCorsConfiguration webCorsConfiguration;

    @Value("${horizon.web.hostname}")
    private String hostname;

    @Value("${server.servlet.context-path}")
    private String context;

    @Bean
    @Order(10)
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .cors(c -> c.configurationSource(webCorsConfiguration))
                .csrf(CsrfConfigurer::disable)
                .securityMatcher(oAuth2AuthorizationServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(a -> a
                        .anyRequest().authenticated()
                )
                .with(oAuth2AuthorizationServerConfigurer, server -> server
                        .authorizationServerSettings(AuthorizationServerSettings.builder()
                                .issuer(hostname + context)
                                .build())
                        .oidc(oidc -> oidc
                                .userInfoEndpoint(ui -> ui.userInfoMapper(userInfoMapper()))))
                .exceptionHandling(c -> c
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        return http.build();
    }

//    @Bean
//    @Order(20)
//    public SecurityFilterChain authenticationFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/login", "/logout")
//                .cors(c -> c.configurationSource(webCorsConfiguration))
//                .csrf(CsrfConfigurer::disable)
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .successHandler((req, res, auth) -> {
//                            res.resetBuffer();
//                            res.setStatus(HttpStatus.OK.value());
//                            res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//                            // fetch saved url (visited before being redirected to login page) and return it in response body.
//                            var savedReq = new HttpSessionRequestCache().getRequest(req, res);
//                            res.getWriter()
//                                    .append("{\"redirectUrl\": \"")
//                                    .append(savedReq == null ? "" : savedReq.getRedirectUrl())
//                                    .append("\"}");
//                            res.flushBuffer();
//                        })
//                        .failureHandler((req, res, ex) -> {
//                            res.setStatus(HttpStatus.UNAUTHORIZED.value());
//                        })
//                )
//                .logout(logout -> logout
//                        .logoutSuccessHandler((req, res, auth) -> {
//                            res.resetBuffer();
//                            res.setStatus(HttpStatus.OK.value());
//                            res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//                            res.getWriter().append("{\"success\": true}");
//                            res.flushBuffer();
//                        })
//                )
//                .exceptionHandling(handler -> handler
//                        .authenticationEntryPoint(
//                                new Http403ForbiddenEntryPoint()
//                        ))
//                .authorizeHttpRequests(a -> a.anyRequest().authenticated());
//
//        return http.build();
//    }

    @Bean
    @Order(30)
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/login", "/logout", "/*.css")
                .cors(c -> c.configurationSource(webCorsConfiguration))
                .csrf(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    @Order(40)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/public/**", "/health/**")
                .cors(c -> c.configurationSource(webCorsConfiguration))
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(a -> a.anyRequest().permitAll())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    @Order(100)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(webCorsConfiguration))
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .anyRequest().authenticated()
                )
                .sessionManagement(c -> c
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
        return http.build();
    }

    public Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper() {
        return (context) -> {
            OAuth2Authorization authorization = context.getAuthorization();
            Authentication principal = context.getAuthentication();

            OidcUserInfo.Builder subject = OidcUserInfo.builder()
                    .subject(authorization.getPrincipalName());

            log.info("Creating USER-INFO " + principal.getPrincipal());

            if (principal.getPrincipal() instanceof JwtAuthenticationToken jwtAuthenticationToken) {
                Jwt token = jwtAuthenticationToken.getToken();
                log.info("TOKEN " + token.getClaims());
                subject
                        .name(token.getClaimAsString("name"))
                        .claim("roles", token.getClaimAsStringList("roles"));
            }

            return subject.build();
        };
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthorityPrefix("ROLE_");
        authoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return authenticationConverter;
    }
}
