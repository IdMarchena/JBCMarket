package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoChat;

import java.time.LocalDateTime;

public record MensajeDto(
        Long id,
        Long idChat,
        Long idSender,
        String contenido,
        EstadoChat estado,
        LocalDateTime fecha
) {}
