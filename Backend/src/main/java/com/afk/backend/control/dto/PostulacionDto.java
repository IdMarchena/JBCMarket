package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record PostulacionDto(
        Long id,
        String contenidoPostulacion,
        Long idVacante,
        Long idUsuarioPostulante,
        Long idUsuarioGerente,
        String estadoPostulacion,
        LocalDateTime fechaInicioPostulacion,
        LocalDateTime fechaFinPostulacion
) {}