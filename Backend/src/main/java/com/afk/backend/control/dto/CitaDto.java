package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoCita;
import java.time.LocalDateTime;

public record CitaDto(
        Long id,
        LocalDateTime fecha,
        Long idUsuarioPostulante,
        Long idPostulacion,
        EstadoCita estadoCita,
        Long idEmpresa
) {}