package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SancionRepository extends JpaRepository<Sancion, Long> {
}
