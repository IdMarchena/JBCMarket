package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record CitaDto(
        Long id,
        LocalDateTime fecha,
        Long idUsuarioPostulante,
        Long idPostulacion,
        String estadoCita,
        Long idEmpresa
) {}