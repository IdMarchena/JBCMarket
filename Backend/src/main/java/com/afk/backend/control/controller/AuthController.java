package com.afk.backend.control.controller;

import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.control.dto.*;
import com.afk.backend.control.security.jwt.JwtUtil;
import com.afk.backend.control.service.impl.UbicacionServiceImpl;
import com.afk.backend.control.service.impl.UsuarioRegistradoServiceImpl;
import com.afk.backend.control.service.impl.UsuarioRolServiceImpl;
import com.afk.backend.control.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRegistradoServiceImpl usuarioRegistradoServiceImpl;
    private final UsuarioRolServiceImpl usuarioRolServiceImpl;
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final UbicacionServiceImpl ubicacionServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtil.generateJwtToken(authentication);

            UsuarioRegistradoDto user = usuarioRegistradoServiceImpl.findUsuarioRegistradoByEmail(loginRequest.username());

            List<UsuarioRolDto> rolesActivos = usuarioRolServiceImpl.findAllByUsuarioRegistradoAndEstadoUsuarioRol(
                    user,
                    "ACTIVO"
            );
            if (rolesActivos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("El usuario no tiene roles activos");
            }

            List<String> roles = rolesActivos.stream()
                    .map(UsuarioRolDto::estadoNombre) // Obtener el nombre del enum
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new JwtResponse(
                            jwtToken,
                            "Bearer",
                            user.username(),
                            roles
                    )
            );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Error de autenticación: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest sRequest) {
        try {
            if (usuarioServiceImpl.existsByCorreo(sRequest.correo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: El correo electrónico ya está en uso.");
            }

            UbicacionDt ubicacion = ubicacionServiceImpl.findUbicacionById(1L);

            UsuarioRegistradoDto nuevoUsuario = new UsuarioRegistradoDto(
                    null,
                    sRequest.nombre(),
                    sRequest.correo(),
                    null,
                    1L,
                    "USER",
                    ubicacion.id_ubicacion(),
                    LocalDateTime.now(),
                    "ACTIVO",
                    sRequest.nombre(),
                    passwordEncoder.encode(sRequest.contrasenia())
            );

            UsuarioRegistradoDto savedUsuario = usuarioRegistradoServiceImpl.createUsuarioRegistrado(nuevoUsuario);

            UsuarioRolDto usuarioRol = new UsuarioRolDto(
                    null,
                    savedUsuario.id(),
                    savedUsuario.rolId(),
                    LocalDateTime.now(),
                    savedUsuario.estadoNombre(),
                    null
            );

            usuarioRolServiceImpl.createUsuarioRol(usuarioRol);

            return ResponseEntity.ok("Usuario registrado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error en el registro: " + e.getMessage());
        }
    }
    @GetMapping("/login")
    public ResponseEntity<String> loginGetFallback() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Debes usar POST para iniciar sesión.");
    }


}

