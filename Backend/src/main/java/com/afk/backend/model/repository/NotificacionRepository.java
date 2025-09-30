package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
