package com.afk.backend.control.controller;

import com.afk.backend.control.dto.JwtResponse;
import com.afk.backend.control.security.service.UserDetailsImpl;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import com.afk.backend.model.entity.enm.Roles;
import com.afk.backend.model.repository.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OAuth2SuccessController {

    private final UsuarioRegistradoRepository usuarioRegistradoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UbicacionRepository ubicacionRepository;

    @GetMapping("/loginSuccess")
    @ResponseBody
    public ResponseEntity<?> loginSuccess(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl principal) {
        if (principal == null) {
            System.out.println("❌ No hay usuario autenticado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado.");
        }

        String jwt = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt == null) {
            return ResponseEntity.status(500).body("❌ Token JWT no disponible en cookies");
        }

        String email = principal.getUsername();
        String name = principal.getName();

        System.out.println("✅ Login con Google exitoso");
        System.out.println("📧 Email: " + email);
        System.out.println("👤 Nombre: " + name);

        Optional<UsuarioRegistrado> existingUser = usuarioRegistradoRepository.findByCorreo(email);

        if (existingUser.isEmpty()) {
            System.out.println("🆕 Usuario no encontrado, creando nuevo usuario con OAuth");

            Usuario usuario = Usuario.builder()
                    .correo(email)
                    .nombre(name)
                    .contrasenia("oauth2") // Dummy password
                    .build();
            usuario = usuarioRepository.save(usuario);

            Rol rol = rolRepository.findByRole(Roles.USER)
                    .orElseThrow(() -> new RuntimeException("Rol User no encontrado"));

            Ubicacion ubicacion = ubicacionRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Ubicación por defecto no encontrada"));

            UsuarioRegistrado registrado = UsuarioRegistrado.builder()
                    .nombre(name)
                    .correo(email)
                    .contrasenia("oauth2")
                    .fecha_registro(LocalDateTime.now())
                    .estado_usuario_registrado(EstadoUsuarioRegistrado.ACTIVO)
                    .telefono_usuario("0000000000")
                    .ubicacion(ubicacion)
                    .rol(rol)
                    .build();
            registrado = usuarioRegistradoRepository.save(registrado);

            UsuarioRol usuarioRol = UsuarioRol.builder()
                    .usuarioRegistrado(registrado)
                    .rol(rol)
                    .estadoUsuarioRol(EstadoUsuarioRol.ACTIVO)
                    .fechaActivacionRol(LocalDateTime.now())
                    .build();
            usuarioRolRepository.save(usuarioRol);

            System.out.println("✅ Usuario registrado con éxito a través de Google OAuth2");
            return ResponseEntity.ok(
                    new JwtResponse(
                            jwt,
                            "Bearer",
                            usuario.getCorreo(),
                            Collections.singletonList(usuario.getContrasenia())
                    )
            );
        } else {
            System.out.println("🔁 Usuario ya existe, no se crea uno nuevo");
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/home")).build();
        }
    }
}
