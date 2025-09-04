package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Calificacion;
import com.afk.backend.model.entity.Publicacion;
import com.afk.backend.model.entity.Ubicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    Optional<Calificacion> findByComentario(String comentario);
    Page<Calificacion> findByComentarioContaining(String nombre, Pageable pageable);
}
