package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record HistorialResponse(
        Long id,
        String rolNombre,
        LocalDateTime fechaActivacion,
        LocalDateTime fechaFin,
        String estado
) {}
