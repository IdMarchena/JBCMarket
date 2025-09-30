package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record SancionDto(
        Long id,
        String descripcion,
        Long idUsuario,
        Long idPublicacion,
        LocalDateTime fechaSancion,
        String estadoSancion
) {}