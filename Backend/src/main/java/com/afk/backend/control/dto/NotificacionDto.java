package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoNotificacion;
import java.time.LocalDateTime;

public record NotificacionDto(
        Integer id,
        String mensaje,
        LocalDateTime fecha,
        Long idUsuario,
        Long idEmpresa,
        EstadoNotificacion estado
) {}