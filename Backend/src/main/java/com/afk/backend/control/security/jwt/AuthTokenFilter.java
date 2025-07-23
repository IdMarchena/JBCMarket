package com.afk.backend.control.security.jwt;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT refactorizado siguiendo protocolo estricto
 * Elimina todas las inconsistencias y duplicaciones del sistema anterior
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtProtocolConfig protocolConfig;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        String method = request.getMethod();

        log.debug("Procesando request: {} {}", method, requestPath);

        try {
            // 1. Permitir peticiones OPTIONS sin autenticación (CORS preflight)
            if ("OPTIONS".equals(method)) {
                log.debug("Permitiendo petición OPTIONS: {}", requestPath);
                filterChain.doFilter(request, response);
                return;
            }

            // 2. Verificar si es endpoint público
            if (protocolConfig.isPublicEndpoint(requestPath)) {
                log.debug("Acceso a endpoint público: {}", requestPath);
                filterChain.doFilter(request, response);
                return;
            }

            // 3. Procesar autenticación JWT para endpoints protegidos
            processJwtAuthentication(request);

        } catch (Exception e) {
            log.error("Error en JWT filter para {}: {}", requestPath, e.getMessage());
            // No bloqueamos el request, dejamos que Spring Security maneje la falta de auth
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Procesa la autenticación JWT siguiendo el protocolo estricto
     */
    private void processJwtAuthentication(HttpServletRequest request) {
        if (request.getRequestURI().startsWith("/oauth2/") ||
                request.getRequestURI().equals("/api/v1/auth/loginSuccess")) {
            log.debug("Skipping JWT processing for OAuth2 endpoint: {}", request.getRequestURI());
            return;
        }
        // Extraer token usando protocolo estricto
        String authHeader = request.getHeader(JwtProtocolConfig.HEADER_AUTHORIZATION);
        String cookieToken = extractTokenFromCookie(request);
        String token = jwtUtil.extractTokenFromRequest(authHeader, cookieToken);

        if (token == null) {
            log.debug("No se encontró token JWT en el request");
            return;
        }

        // Validar token usando protocolo optimizado
        JwtValidationResult validationResult = jwtUtil.validateAndParseToken(token);

        if (!validationResult.isValid()) {
            log.debug("Token JWT inválido: {}", validationResult.getErrorMessage());
            return;
        }

        String username = validationResult.getUsername();
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticateUser(request, username);
        }
    }

    /**
     * Autentica al usuario en el contexto de seguridad
     */
    private void authenticateUser(HttpServletRequest request, String username) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("Usuario autenticado exitosamente: {}", username);

        } catch (Exception e) {
            log.error("Error al autenticar usuario {}: {}", username, e.getMessage());
        }
    }

    /**
     * Extrae token JWT de las cookies
     */
    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (JwtProtocolConfig.COOKIE_TOKEN_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
                // Soporte para cookie legacy "token"
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

