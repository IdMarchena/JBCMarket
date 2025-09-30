package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Usuario;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombre(String nombre);
    boolean existsById(@NonNull Long id);
    boolean existsByCorreo(String correo);
    boolean existsByNombre(String nombre);
    Page<Usuario> findByCorreoContaining(String correo, Pageable pageable);

    @Query("SELECT ur.usuarioRegistrado FROM UsuarioRol ur " +
            "WHERE ur.id_usuario = :id_usuario ")
    Usuario findUsuarioByIdUsuario(Long id_usuario);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.perfil WHERE u.id = :id")
    Optional<Usuario> findByIdWithPerfil(Long id);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.perfil")
    List<Usuario> findAllWithPerfil();


}
