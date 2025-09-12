package com.afk.backend.control.security.service;

import com.afk.backend.control.security.jwt.JwtProtocolConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtSecurityConfig {

    private final JwtProtocolConfig protocolConfig;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeAndValidateJwtProtocol() {
        try {
            // Validaciones básicas del protocolo
            if (protocolConfig.getJwtSecret() == null || protocolConfig.getJwtSecret().trim().isEmpty()) {
                throw new IllegalStateException("JWT Secret no configurado. Verificar property: app.jwt.secret");
            }

            if (protocolConfig.getJwtExpirationMs() <= 0) {
                throw new IllegalStateException("JWT Expiration debe ser mayor a 0");
            }

            if (protocolConfig.getJwtIssuer() == null || protocolConfig.getJwtIssuer().trim().isEmpty()) {
                log.warn("JWT Issuer no configurado. Usando valor por defecto.");
            }

            log.info("=== JWT SECURITY PROTOCOL INITIALIZED ===");
            log.info("🔐 JWT Configuration:");
            log.info("   ├─ Expiration: {} minutes", protocolConfig.getExpirationMinutes());
            log.info("   ├─ Issuer: {}", protocolConfig.getJwtIssuer());
            log.info("   ├─ Secret configured: ✓");
            log.info("   └─ Cookie name: {}", JwtProtocolConfig.COOKIE_TOKEN_NAME);

            log.info("🌐 Public Endpoints:");
            JwtProtocolConfig.PUBLIC_ENDPOINTS.forEach(endpoint ->
                    log.info("   ├─ {}", endpoint)
            );

            log.info("🔒 Protected endpoints: All others require JWT authentication");
            log.info("✅ JWT Protocol validation passed");
            log.info("=======================================");

        } catch (Exception e) {
            log.error("❌ JWT Protocol validation failed: {}", e.getMessage());
            throw new RuntimeException("JWT Protocol misconfiguration", e);
        }
    }
}
