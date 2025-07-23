package com.afk.backend.control.security.jwt;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

/**
 * Configuración centralizada del protocolo JWT
 * Elimina las inconsistencias del sistema anterior
 */
@Component
@Slf4j
@Getter
public class JwtProtocolConfig {

    // Configuración JWT desde properties
    @Value("${spring.security.oauth2.resourceserver.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expirationMs}")// 30 minutos por defecto
    private long jwtExpirationMs;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String jwtIssuer;

    // Claims estándar del protocolo JWT
    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_AUTHORITIES = "authorities";

    // Headers JWT
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_BEARER_PREFIX = "Bearer ";
    public static final String COOKIE_TOKEN_NAME = "jwt_token";

    // Endpoints públicos que NO requieren JWT
    public static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh",
            "/api/v1/auth/loginSuccess",
            "/oauth2/**",
            "/login**",
            "/hello",
            "/error",
            "/actuator/health",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/favicon.ico",
            "/static/**"
    );

    // Configuración de cookies JWT
    public static final boolean COOKIE_HTTP_ONLY = true;
    public static final boolean COOKIE_SECURE = true;
    public static final String COOKIE_PATH = "/";

    /**
     * Obtiene la duración del token en minutos
     */
    public long getExpirationMinutes() {
        return Duration.ofMillis(jwtExpirationMs).toMinutes();
    }

    /**
     * Verifica si un endpoint es público
     */
    public boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream()
                .anyMatch(publicPath -> {
                    if (publicPath.endsWith("/**")) {
                        return path.startsWith(publicPath.substring(0, publicPath.length() - 3));
                    }
                    if (publicPath.endsWith("**")) {
                        return path.startsWith(publicPath.substring(0, publicPath.length() - 2));
                    }
                    return path.equals(publicPath);
                });
    }
}
