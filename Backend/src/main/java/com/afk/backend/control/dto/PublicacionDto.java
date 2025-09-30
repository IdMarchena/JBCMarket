package com.afk.backend.control.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PublicacionDto(
        Long id,
        String titulo,
        String descripcion,
        Long idVacante,
        LocalDateTime fechaPublicacion,
        String estadoPublicacion,
        List<CalificacionDto> calificaciones
) {}