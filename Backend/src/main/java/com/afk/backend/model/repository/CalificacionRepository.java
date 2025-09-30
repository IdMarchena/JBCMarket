package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Calificacion;
import com.afk.backend.model.entity.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    Page<Calificacion> findByComentarioContaining(String nombre, Pageable pageable);
    List<Calificacion> findByPublicacion_Id(Long idPublicacion);
}
