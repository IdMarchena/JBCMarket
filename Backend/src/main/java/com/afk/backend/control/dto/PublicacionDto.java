package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoPublicacion;
import java.time.LocalDateTime;
import java.util.List;

public record PublicacionDto(
        Long id,
        String titulo,
        String descripcion,
        Long idVacante,
        LocalDateTime fechaPublicacion,
        EstadoPublicacion estadoPublicacion,
        List<Long> calificacionesIds
) {}