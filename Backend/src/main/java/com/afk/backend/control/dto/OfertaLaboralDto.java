package com.afk.backend.control.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OfertaLaboralDto(
        Long id,
        LocalDateTime fechaInicioSistema,
        LocalDateTime fechaFinSistema,
        List<Long> idsHistorialPostulantes,
        List<Long> idsVacantes,
        List<Long> idsCalificaciones,
        List<Long> idsCitas,
        List<Long> idsFavoritos,
        List<Long> idsPublicaciones,
        List<Long> idsEmpresas
) {}