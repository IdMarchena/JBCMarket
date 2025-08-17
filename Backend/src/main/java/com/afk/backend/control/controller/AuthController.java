package com.afk.backend.control.controller;

import com.afk.backend.control.dto.JwtResponse;
import com.afk.backend.control.dto.LoginRequest;
import com.afk.backend.control.dto.SignUpRequest;
import com.afk.backend.control.security.jwt.JwtUtil;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.afk.backend.model.entity.Rol;

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
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioRegistradoRepository usuarioRegistradoRepository;
    private final UbicacionRepository ubicacionRepository;

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

            UsuarioRegistrado user = usuarioRegistradoRepository.findByCorreo(loginRequest.username())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            List<UsuarioRol> rolesActivos = usuarioRolRepository.findAllByUsuarioRegistradoAndEstadoUsuarioRol(
                    user,
                    EstadoUsuarioRol.ACTIVO
            );
            if (rolesActivos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("El usuario no tiene roles activos");
            }

            List<String> roles = rolesActivos.stream()
                    .map(ur -> ur.getRol().getRole().name()) // Obtener el nombre del enum
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new JwtResponse(
                            jwtToken,
                            "Bearer",
                            user.getCorreo(),
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
            if (usuarioRepository.existsByCorreo(sRequest.correo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: El correo electrónico ya está en uso.");
            }

            Rol rol = rolRepository.findByRole(sRequest.rol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + sRequest.rol()));

            Ubicacion ubicacion = ubicacionRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

            Usuario usuario = Usuario.builder()
                    .nombre(sRequest.nombre())
                    .correo(sRequest.correo())
                    .contrasenia(passwordEncoder.encode(sRequest.contrasenia()))
                    .build();



            UsuarioRegistrado nuevoUsuario = UsuarioRegistrado.builder()

                    .nombre(sRequest.nombre())
                    .correo(sRequest.correo())
                    .contrasenia(usuario.getContrasenia())
                    .ubicacion(ubicacion)
                    .fecha_registro(LocalDateTime.now())
                    .estado_usuario_registrado(EstadoUsuarioRegistrado.ACTIVO)
                    .telefono_usuario(sRequest.cel())
                    .rol(rol)
                    .build();

            UsuarioRegistrado savedUsuario = usuarioRegistradoRepository.save(nuevoUsuario);

            UsuarioRol usuarioRol = UsuarioRol.builder()
                    .usuarioRegistrado(savedUsuario)
                    .rol(rol)
                    .estadoUsuarioRol(EstadoUsuarioRol.ACTIVO)
                    .fechaActivacionRol(LocalDateTime.now())
                    .build();

            usuarioRolRepository.save(usuarioRol);

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

