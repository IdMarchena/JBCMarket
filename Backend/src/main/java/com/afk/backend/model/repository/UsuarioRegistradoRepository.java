package com.afk.backend.model.repository;

import com.afk.backend.model.entity.UsuarioRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRegistradoRepository extends JpaRepository<UsuarioRegistrado, Long> {
    Optional<UsuarioRegistrado> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
