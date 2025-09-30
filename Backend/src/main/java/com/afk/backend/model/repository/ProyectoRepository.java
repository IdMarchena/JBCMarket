package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
}
