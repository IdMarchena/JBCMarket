package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import java.time.LocalDateTime;

public record UsuarioRolDto(
        Long id,
        Long idUsuarioRegistrado,
        Integer idRol,
        LocalDateTime fechaActivacion,
        EstadoUsuarioRol estado,
        LocalDateTime fechaFin
) {}