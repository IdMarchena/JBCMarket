package com.afk.backend.control.security.oauth2;

import com.afk.backend.model.entity.Rol;
import com.afk.backend.model.entity.UsuarioRegistrado;
import com.afk.backend.model.entity.UsuarioRol;
import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import com.afk.backend.model.entity.enm.Roles;
import com.afk.backend.model.repository.RolRepository;
import com.afk.backend.model.repository.UsuarioRegistradoRepository;
import com.afk.backend.model.repository.UsuarioRolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsuarioRegistradoRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String correo = (String) attributes.get("email");
        String nombre = (String) attributes.get("name");


        Optional<UsuarioRegistrado> existente = usuarioRepository.findByCorreo(correo);
        if (existente.isEmpty()) {

            Rol rolDefault = rolRepository.findByRole(Roles.ROLE_POSTULANTE)
                    .orElseThrow(() -> new RuntimeException("Rol ROL_POSTULANTE no encontrado"));

            UsuarioRegistrado nuevo = UsuarioRegistrado.builder()
                    .nombre(nombre)
                    .correo(correo)
                    .contrasenia("oauth2_user") // Puedes colocar un valor fijo
                    .fecha_registro(LocalDateTime.now())
                    .estado_usuario_registrado(EstadoUsuarioRegistrado.ACTIVO)
                    .telefono_usuario("0000000000") // Fijo temporal
                    .rol(rolDefault)
                    .ubicacion(null) // Coloca una ubicación por defecto si es obligatoria
                    .build();

            UsuarioRegistrado guardado = usuarioRepository.save(nuevo);

            UsuarioRol usuarioRol = UsuarioRol.builder()
                    .usuarioRegistrado(guardado)
                    .rol(rolDefault)
                    .estadoUsuarioRol(EstadoUsuarioRol.ACTIVO)
                    .fechaActivacionRol(LocalDateTime.now())
                    .build();

            usuarioRolRepository.save(usuarioRol);

            System.out.println("✅ Usuario creado desde Google: " + correo);
        } else {
            System.out.println("🔁 Usuario ya existente desde Google: " + correo);
        }

        return oAuth2User;
    }
}