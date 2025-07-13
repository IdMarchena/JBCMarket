package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record VacanteDto(
        Long id,
        String nombre,
        String descripcion,
        Long idUbicacion,
        Long idUsuarioGerente,
        Long idEmpresa,
        LocalDateTime fechaVcante
) {}
