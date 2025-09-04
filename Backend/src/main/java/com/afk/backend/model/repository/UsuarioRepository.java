package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombre(String nombre);
    boolean existsById(Long id);
    boolean existsByCorreo(String correo);
    Page<Usuario> findByCorreoContaining(String correo, Pageable pageable);
}
