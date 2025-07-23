package com.afk.backend.control.security.oauth2;

import com.afk.backend.control.security.service.UserDetailsImpl;
import com.afk.backend.model.entity.Rol;
import com.afk.backend.model.entity.Ubicacion;
import com.afk.backend.model.entity.UsuarioRegistrado;
import com.afk.backend.model.entity.UsuarioRol;
import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import com.afk.backend.model.entity.enm.Roles;
import com.afk.backend.model.repository.RolRepository;
import com.afk.backend.model.repository.UbicacionRepository;
import com.afk.backend.model.repository.UsuarioRegistradoRepository;
import com.afk.backend.model.repository.UsuarioRolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Servicio OAuth2 optimizado siguiendo protocolo JWT
 * Elimina dependencias innecesarias y mejora la gestión de usuarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsuarioRegistradoRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UbicacionRepository ubicacionRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.debug("Procesando usuario OAuth2 desde: {}", registrationId);

        String correo = extractEmail(userRequest, attributes, registrationId);
        String nombre = extractName(attributes, registrationId);

        if (correo == null) {
            throw new RuntimeException("No se pudo obtener el correo del proveedor OAuth2: " + registrationId);
        }

        // Modificar atributos para incluir el correo correcto
        Map<String, Object> modifiedAttributes = new HashMap<>(attributes);
        modifiedAttributes.put("email", correo);

        // Buscar o crear usuario
        UsuarioRegistrado user = findOrCreateUser(correo, nombre);

        // Obtener roles activos
        List<SimpleGrantedAuthority> authorities = getUserAuthorities(user);

        log.info("Usuario OAuth2 procesado exitosamente: {}", correo);

        return UserDetailsImpl.build(user, authorities, modifiedAttributes);
    }

    /**
     * Extrae el email del usuario según el proveedor OAuth2
     */
    private String extractEmail(OAuth2UserRequest userRequest, Map<String, Object> attributes, String registrationId) {
        String correo = null;

        if ("google".equals(registrationId)) {
            correo = (String) attributes.get("email");
        } else if ("github".equals(registrationId)) {
            correo = (String) attributes.get("email");

            // Si GitHub no proporciona email público, intentar obtener el primario
            if (correo == null) {
                correo = fetchGithubPrimaryEmail(userRequest);
                if (correo == null) {
                    throw new RuntimeException("⚠️ GitHub no retornó correo. Asegúrate de que el correo no sea privado y estés pidiendo el scope `user:email`.");
                }
            }
        }

        return correo;
    }

    /**
     * Extrae el nombre del usuario según el proveedor OAuth2
     */
    private String extractName(Map<String, Object> attributes, String registrationId) {
        return (String) attributes.get("name");
    }

    /**
     * Busca un usuario existente o crea uno nuevo
     */
    private UsuarioRegistrado findOrCreateUser(String correo, String nombre) {
        Optional<UsuarioRegistrado> existente = usuarioRepository.findByCorreo(correo);

        if (existente.isPresent()) {
            log.debug("Usuario existente encontrado: {}", correo);
            return existente.get();
        }

        log.debug("Creando nuevo usuario OAuth2: {}", correo);
        return createNewUser(correo, nombre);
    }

    /**
     * Crea un nuevo usuario con configuración por defecto
     */
    private UsuarioRegistrado createNewUser(String correo, String nombre) {
        Rol rolDefault = rolRepository.findByRole(Roles.ROLE_POSTULANTE)
                .orElseThrow(() -> new RuntimeException("Rol ROLE_POSTULANTE no encontrado"));

        Ubicacion ubicacionDefault = ubicacionRepository.findByNombre("Santa Marta")
                .orElseThrow(() -> new RuntimeException("Ubicación por defecto no encontrada"));

        UsuarioRegistrado user = UsuarioRegistrado.builder()
                .nombre(nombre != null ? nombre : "Usuario OAuth2")
                .correo(correo)
                .contrasenia("oauth2_user") // Password placeholder para usuarios OAuth2
                .fecha_registro(LocalDateTime.now())
                .estado_usuario_registrado(EstadoUsuarioRegistrado.ACTIVO)
                .telefono_usuario("0000000000") // Teléfono placeholder
                .rol(rolDefault)
                .ubicacion(ubicacionDefault)
                .build();

        user = usuarioRepository.save(user);

        // Crear relación usuario-rol
        UsuarioRol usuarioRol = UsuarioRol.builder()
                .usuarioRegistrado(user)
                .rol(rolDefault)
                .estadoUsuarioRol(EstadoUsuarioRol.ACTIVO)
                .fechaActivacionRol(LocalDateTime.now())
                .build();

        usuarioRolRepository.save(usuarioRol);

        log.info("✅ Usuario OAuth2 creado exitosamente: {}", correo);
        return user;
    }

    /**
     * Obtiene las autoridades del usuario
     */
    private List<SimpleGrantedAuthority> getUserAuthorities(UsuarioRegistrado user) {
        Optional<UsuarioRol> rolesActivos = usuarioRolRepository
                .findByUsuarioRegistradoAndEstadoUsuarioRol(user, EstadoUsuarioRol.ACTIVO);

        return rolesActivos.stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRol().getRole().name()))
                .toList();
    }

    /**
     * Obtiene el email primario de GitHub usando la API
     */
    private String fetchGithubPrimaryEmail(OAuth2UserRequest userRequest) {
        String token = userRequest.getAccessToken().getTokenValue();

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "token " + token);
            headers.add("Accept", "application/vnd.github.v3+json");

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    "https://api.github.com/user/emails",
                    HttpMethod.GET,
                    entity,
                    (Class<List<Map<String, Object>>>) (Class<?>) List.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Object>> emails = response.getBody();

                for (Map<String, Object> emailEntry : emails) {
                    Boolean primary = (Boolean) emailEntry.get("primary");
                    Boolean verified = (Boolean) emailEntry.get("verified");
                    String email = (String) emailEntry.get("email");

                    if (Boolean.TRUE.equals(primary) && Boolean.TRUE.equals(verified)) {
                        log.debug("Email primario de GitHub obtenido: {}", email);
                        return email;
                    }
                }
            }

            log.warn("No se encontró email primario verificado en GitHub");
            return null;

        } catch (Exception e) {
            log.error("Error al obtener email primario de GitHub: {}", e.getMessage());
            return null;
        }
    }

}
