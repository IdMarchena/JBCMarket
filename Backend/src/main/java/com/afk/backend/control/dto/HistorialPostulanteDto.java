package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record HistorialPostulanteDto(
        Long id,
        LocalDateTime fechaRegistro,
        Long idPostulacion,
        Long idUsuarioPostulante
) {}