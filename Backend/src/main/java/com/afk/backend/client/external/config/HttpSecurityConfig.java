package com.afk.backend.client.external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class HttpSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // ✅ forma correcta a partir de Spring Security 6.1+
                .httpBasic(Customizer.withDefaults()); // puedes cambiarlo por JWT u otra forma de auth

        return http.build();
    }
}
