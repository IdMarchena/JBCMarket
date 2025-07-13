package com.afk.backend.control.dto;

public record RequisitoDto(
        Long id,
        String descripcion,
        Long idVacante,
        Long idTipoRequisito
) {}