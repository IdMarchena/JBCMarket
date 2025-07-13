package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
}
