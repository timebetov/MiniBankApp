package com.github.timebetov.accounts.config;

import com.github.timebetov.securityUtils.KeyCloakRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers(HttpMethod.POST, "/accounts").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/accounts/me").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PATCH, "/accounts/**").hasAnyRole("ADMIN", "TRANSFER")
                        .requestMatchers(HttpMethod.GET, "/accounts/{userId}").hasAnyRole("ADMIN", "TRANSFER")
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(serverConf -> serverConf.jwt(
                        jwtConf -> jwtConf.jwtAuthenticationConverter(jwtAuthenticationConverter())
                ));
        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());
        return jwtAuthenticationConverter;
    }
}
