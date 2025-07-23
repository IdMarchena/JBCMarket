package com.afk.backend.control.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Resultado de validación JWT siguiendo protocolo estricto
 * Encapsula toda la información de validación de manera consistente
 */
@Getter
@Builder
public class JwtValidationResult {
    private final boolean valid;
    private final boolean expired;
    private final Claims claims;
    private final String username;
    private final String email;
    private final Long userId;
    private final List<String> authorities;
    private final String errorMessage;

    /**
     * Resultado para token válido
     */
    public static JwtValidationResult valid(Claims claims, String username, String email, Long userId, List<String> authorities) {
        return JwtValidationResult.builder()
                .valid(true)
                .expired(false)
                .claims(claims)
                .username(username)
                .email(email)
                .userId(userId)
                .authorities(authorities)
                .build();
    }

    /**
     * Resultado para token expirado
     */
    public static JwtValidationResult expired() {
        return JwtValidationResult.builder()
                .valid(false)
                .expired(true)
                .errorMessage("Token JWT expirado")
                .build();
    }

    /**
     * Resultado para token inválido
     */
    public static JwtValidationResult invalid() {
        return JwtValidationResult.builder()
                .valid(false)
                .expired(false)
                .errorMessage("Token JWT inválido")
                .build();
    }

    /**
     * Resultado para token no encontrado
     */
    public static JwtValidationResult notFound() {
        return JwtValidationResult.builder()
                .valid(false)
                .expired(false)
                .errorMessage("Token JWT no encontrado")
                .build();
    }
}
