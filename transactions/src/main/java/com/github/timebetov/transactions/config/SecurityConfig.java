package com.github.timebetov.transactions.config;

import com.github.timebetov.securityUtils.KeyCloakRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(scm -> scm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .anyRequest().hasRole("CUSTOMER"))
                .oauth2ResourceServer(serverConf -> serverConf.jwt(
                        jwtConf -> jwtConf.jwtAuthenticationConverter(jwtAuthenticationConverter())
                ));
        return http.oauth2Client(Customizer.withDefaults()).build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());
        return jwtAuthenticationConverter;
    }
}
