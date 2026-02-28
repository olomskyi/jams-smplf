package com.olomsky.gateway.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final String[] unSecureUrls = {"/swagger-ui.html", "/swagger-ui/**",
                                           "/vs/api-docs/**", "/swagger-resources/**",
                                           "/api-docs/**", "aggregate/**"};

    @Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                    @Qualifier("corsConfigSource")
                                                    CorsConfigurationSource corsConfigSource) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigSource))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(unSecureUrls)
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = "http://keycloak:8080/realms/jams-front-angular-realm/protocol/openid-connect/certs";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}
