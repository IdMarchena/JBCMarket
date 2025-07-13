package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoSancion;
import java.time.LocalDateTime;

public record SancionDto(
        Long id,
        String descripcion,
        Long idUsuario,
        Long idPublicacion,
        LocalDateTime fechaSancion,
        EstadoSancion estadoSancion
) {}