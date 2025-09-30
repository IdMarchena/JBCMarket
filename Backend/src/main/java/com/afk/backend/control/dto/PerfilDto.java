package com.afk.backend.control.dto;

import java.util.List;
public record PerfilDto(
        Long id,
        String perfilName,
        String descripcion,
        List<ProyectoDto> proyectos,
        String urlForto,
        Long idUsuario) {
}
