package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Usuario;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombre(String nombre);
    boolean existsById(@NonNull Long id);
    boolean existsByCorreo(String correo);
    Page<Usuario> findByCorreoContaining(String correo, Pageable pageable);

    @Query("SELECT ur.usuarioRegistrado FROM UsuarioRol ur " +
            "JOIN ur.rol r " +
            "WHERE ur.id_usuario = :id_usuario " +
            "AND r.role = 'ROLE_POSTULANTE'")
    Usuario findUsuarioByIdUsuario(Long id_usuario);
}
