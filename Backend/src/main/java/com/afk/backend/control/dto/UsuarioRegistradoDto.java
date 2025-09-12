package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;
import java.time.LocalDateTime;

public record UsuarioRegistradoDto(
        Long id,
        String nombre,
        String email,
        String telefono,
        Long rolId,
        String rolNombre,
        Long ubicacionId,
        LocalDateTime fechaRegistro,
        String estadoNombre,
        String username,
        String password
) {}