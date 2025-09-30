package com.afk.backend.control.controller;
import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.control.dto.*;
import com.afk.backend.control.security.jwt.JwtUtil;
import com.afk.backend.control.service.SmsService;
import com.afk.backend.control.service.impl.UbicacionServiceImpl;
import com.afk.backend.control.service.impl.UsuarioRegistradoServiceImpl;
import com.afk.backend.control.service.impl.UsuarioRolServiceImpl;
import com.afk.backend.control.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
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
    private final SmsService smsService;

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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest sRequest) {
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
    @PostMapping("/signup/phone")
    public ResponseEntity<?> requestPhoneSignup(@RequestBody SignUpPhoneRequest sRequest) {
        try {
            if (usuarioServiceImpl.existsByNombre(sRequest.nombre())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: El nombre de usuario ya está en uso.");
            }

            // Generar OTP y enviarlo al teléfono
            smsService.generateAndSendOtp(sRequest.telefono());

            // Retornar una respuesta temporal
            return ResponseEntity.ok("OTP enviado al número " + sRequest.telefono());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error en el registro con teléfono: " + e.getMessage());
        }
    }
    @PostMapping("/verify/otp")
    public ResponseEntity<?> verifyOtpAndRegister(@Valid @RequestBody VerifyOtpRequest request) {
        if (request.phoneNumber() == null || request.otp() == null) {
            log.warn("❌ Datos incompletos: phoneNumber={} otp={}", request.phoneNumber(), request.otp());
            return ResponseEntity.badRequest().body("Número de teléfono y OTP son obligatorios.");
        }
        boolean isValid = smsService.validateOtp(request.phoneNumber(), request.otp());

        if (!isValid) {
            log.warn("❌ OTP inválido o expirado para número {}", request.phoneNumber());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("❌ OTP inválido o expirado.");
        }

        try {
            if (usuarioServiceImpl.existsByCorreo(request.correo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: El correo electrónico ya está en uso.");
            }
            UbicacionDt ubicacion = ubicacionServiceImpl.findUbicacionById(1L);

            UsuarioRegistradoDto nuevoUsuario = new UsuarioRegistradoDto(
                    null,
                    request.nombre(),
                    request.correo(),
                    request.phoneNumber(),
                    1L,
                    "USER",
                    ubicacion.id_ubicacion(),
                    LocalDateTime.now(),
                    "ACTIVO",
                    request.phoneNumber(),
                    passwordEncoder.encode(request.contrasenia())
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
            return ResponseEntity.ok("✅ Usuario registrado correctamente con teléfono.");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar usuario: " + e.getMessage());
        }

    }
    @GetMapping("/login")
    public ResponseEntity<String> loginGetFallback() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body("Debes usar POST para iniciar sesión.");
    }

}

