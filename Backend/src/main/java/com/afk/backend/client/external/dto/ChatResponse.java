package com.afk.backend.client.external.dto;

import com.afk.backend.control.dto.MensajeDto;
import com.afk.backend.model.entity.enm.EstadoChat;

import java.time.LocalDateTime;
import java.util.List;

public record ChatResponse(
        Long id,
        Long senderId,
        Long receiverId,
        String message,
        EstadoChat status,
        LocalDateTime createdAt,
        List<MensajeDto> mensajes
) {}
