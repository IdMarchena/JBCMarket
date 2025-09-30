package com.afk.backend.model.repository;

import com.afk.backend.model.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByChatId(Long chatId);
}
