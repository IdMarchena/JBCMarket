package com.afk.backend.control.controller;

import com.afk.backend.control.security.oauth2.CustomOAuth2User;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import com.afk.backend.model.entity.enm.Roles;
import com.afk.backend.model.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
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
    public String loginSuccess(HttpServletRequest request, @AuthenticationPrincipal CustomOAuth2User principal) {
        String email = principal.getEmail();
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
                    .contrasenia("oauth2") // Contraseña dummy
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
        } else {
            System.out.println("🔁 Usuario ya existe, no se crea uno nuevo");
        }

        return "redirect:/home";
    }
}
