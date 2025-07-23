package com.afk.backend.control.security.jwt;

import com.afk.backend.control.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilidad JWT refactorizada siguiendo protocolo estricto
 * Elimina todas las inconsistencias del sistema anterior
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final JwtProtocolConfig protocolConfig;

    // Cache de componentes JWT para optimización
    private Key signingKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void initializeJwtComponents() {
        try {
            // Inicializar clave de firma una sola vez
            this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(protocolConfig.getJwtSecret()));

            // Inicializar parser JWT una sola vez
            this.jwtParser = Jwts.parser()
                    .setSigningKey(signingKey)
                    .build();

            log.info("JWT Protocol inicializado correctamente");
            log.info("- Expiración: {} minutos", protocolConfig.getExpirationMinutes());
            log.info("- Issuer: {}", protocolConfig.getJwtIssuer());

        } catch (Exception e) {
            log.error("Error inicializando componentes JWT: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize JWT components", e);
        }
    }

    /**
     * Genera un JWT siguiendo el protocolo estricto
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Extraer información adicional si es nuestro UserDetailsImpl
        Long userId = null;
        String email = userDetails.getUsername();

        if (userDetails instanceof UserDetailsImpl userDetailsImpl) {
            userId = userDetailsImpl.getId();
        }

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        long now = System.currentTimeMillis();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(protocolConfig.getJwtIssuer())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + protocolConfig.getJwtExpirationMs()))
                .claim(JwtProtocolConfig.CLAIM_EMAIL, email)
                .claim(JwtProtocolConfig.CLAIM_AUTHORITIES, roles)
                .signWith(signingKey, SignatureAlgorithm.HS256);

        // Agregar userId solo si está disponible
        if (userId != null) {
            jwtBuilder.claim(JwtProtocolConfig.CLAIM_USER_ID, userId);
        }

        return jwtBuilder.compact();
    }

    /**
     * Valida y parsea un JWT en una sola operación (protocolo optimizado)
     */
    public JwtValidationResult validateAndParseToken(String token) {
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();

            return JwtValidationResult.builder()
                    .valid(true)
                    .claims(claims)
                    .username(claims.getSubject())
                    .email(claims.get(JwtProtocolConfig.CLAIM_EMAIL, String.class))
                    .userId(claims.get(JwtProtocolConfig.CLAIM_USER_ID, Long.class))
                    .authorities(claims.get(JwtProtocolConfig.CLAIM_AUTHORITIES, List.class))
                    .build();

        } catch (ExpiredJwtException e) {
            log.debug("JWT token expirado: {}", e.getMessage());
            return JwtValidationResult.expired();
        } catch (JwtException e) {
            log.debug("JWT token inválido: {}", e.getMessage());
            return JwtValidationResult.invalid();
        }
    }

    /**
     * Método simplificado para obtener username (mantiene compatibilidad)
     */
    public String validateAndGetUserName(String token) {
        JwtValidationResult result = validateAndParseToken(token);
        return result.isValid() ? result.getUsername() : null;
    }

    /**
     * Extrae el token del header Authorization o cookie
     */
    public String extractTokenFromRequest(String authHeader, String cookieValue) {
        // Prioridad 1: Header Authorization
        if (authHeader != null && authHeader.startsWith(JwtProtocolConfig.HEADER_BEARER_PREFIX)) {
            return authHeader.substring(JwtProtocolConfig.HEADER_BEARER_PREFIX.length());
        }

        // Prioridad 2: Cookie
        if (cookieValue != null && !cookieValue.trim().isEmpty()) {
            return cookieValue;
        }

        return null;
    }
}

