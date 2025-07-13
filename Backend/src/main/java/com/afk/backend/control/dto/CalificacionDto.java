package com.afk.backend.control.dto;

public record CalificacionDto(
        Long id,
        Integer puntaje,
        String comentario,
        Long idUsuarioPostulante,
        Long idPublicacion
) {}