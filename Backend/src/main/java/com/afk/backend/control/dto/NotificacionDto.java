package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record NotificacionDto(
        Long id,
        String mensaje,
        LocalDateTime fecha,
        Long idUsuario,
        Long idEmpresa,
        String estado
) {}