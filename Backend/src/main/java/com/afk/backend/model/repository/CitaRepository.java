package com.afk.backend.model.repository;
import com.afk.backend.model.entity.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    Page<Cita> findByFechaContaining(LocalDateTime fecha, Pageable pageable);
}
