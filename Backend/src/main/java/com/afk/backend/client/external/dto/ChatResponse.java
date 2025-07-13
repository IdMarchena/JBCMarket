package com.afk.backend.client.external.dto;

import com.afk.backend.model.entity.enm.EstadoChat;

import java.time.LocalDateTime;

public record ChatResponse(
        Long id,
        Long senderId,
        Long receiverId,
        String message,
        EstadoChat status,
        LocalDateTime createdAt
) {}
