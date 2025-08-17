package com.afk.backend.client.external.config;

import com.afk.backend.control.dto.JwtResponse;
import com.afk.backend.control.security.jwt.AuthTokenFilter;
import com.afk.backend.control.security.jwt.JwtProtocolConfig;
import com.afk.backend.control.security.jwt.JwtUtil;
import com.afk.backend.control.security.oauth2.CustomOAuth2UserService;
import com.afk.backend.control.security.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final AuthTokenFilter authTokenFilter;
    private final JwtUtil jwtUtil;
    private final JwtProtocolConfig protocolConfig;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Configurando Security Filter Chain con protocolo JWT");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of("http://localhost:9000"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/oauth2/**",  // Add this line for OAuth2 callback
                                "/login/oauth2/**"  // Add this line for OAuth2 callback
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(oauth2 -> oauth2

                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(this::handleOAuth2Success)
                        .failureHandler(this::handleOAuth2Failure)
                )
                .formLogin(form -> form.disable())
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults());

        return http.build();
    }

    private void handleOAuth2Success(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Authentication authentication) throws IOException {
        try {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String email = oauth2User.getAttribute("email");

            if (email == null) {
                throw new IllegalArgumentException("Email no encontrado en los atributos de OAuth2");
            }

            log.debug("Procesando OAuth2 success para email: {}", email);

            // Cargar o crear el usuario en tu sistema
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);

            // Crear nueva autenticación con UserDetails
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // Establecer en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            // Generar JWT
            String jwt = jwtUtil.generateJwtToken(newAuth);

            // Configurar cookie segura
            Cookie jwtCookie = new Cookie(JwtProtocolConfig.COOKIE_TOKEN_NAME, jwt);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge((int) (protocolConfig.getJwtExpirationMs() / 1000));
            response.addCookie(jwtCookie);

            // Crear respuesta JSON directamente
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(
                    new JwtResponse(
                            jwt,
                            "Bearer",
                            email,
                            userDetails.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList())
                    )
            ));

            log.info("Autenticación OAuth2 exitosa para usuario: {}", email);

        } catch (Exception e) {
            log.error("Error en OAuth2 success handler: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"error\":\"Error procesando autenticación OAuth2\"}");
        }
    }

    private void handleOAuth2Failure(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Exception exception) throws IOException {
        log.error("Fallo en autenticación OAuth2: {}", exception.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\":\"" + exception.getMessage() + "\"}");
    }
}