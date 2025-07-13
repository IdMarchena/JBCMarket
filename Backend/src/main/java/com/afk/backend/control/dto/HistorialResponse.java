package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoUsuarioRol;

import java.time.LocalDateTime;

public record HistorialResponse(
        Long id,
        String rolNombre,
        LocalDateTime fechaActivacion,
        LocalDateTime fechaFin,
        EstadoUsuarioRol estado,
        String modificadoPorNombre
) {}
