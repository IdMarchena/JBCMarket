package com.afk.backend.client.external.dto;

import com.afk.backend.control.dto.MensajeDto;
import com.afk.backend.model.entity.enm.EstadoChat;

import java.util.List;

public record ChatRequest(
        Long senderId,
        Long receiverId,
        String message,
        EstadoChat status,
        List<MensajeDto> mensajes
) {}
