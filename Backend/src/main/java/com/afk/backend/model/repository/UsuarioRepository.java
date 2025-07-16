package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsById(Long id);
    boolean existsByCorreo(String correo);
}
