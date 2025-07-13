package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
}
