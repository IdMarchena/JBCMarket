package com.afk.backend.client.external.dto;

import com.afk.backend.model.entity.enm.EstadoChat;

public record ChatRequest(
        Long senderId,
        Long receiverId,
        String message,
        EstadoChat status
) {}
