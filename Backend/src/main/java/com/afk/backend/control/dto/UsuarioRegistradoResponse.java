package com.afk.backend.control.dto;

import com.afk.backend.model.entity.enm.EstadoUsuarioRegistrado;

import java.time.LocalDateTime;

public record UsuarioRegistradoResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String rolNombre,
        String ubicacionNombre,
        LocalDateTime fechaRegistro,
        EstadoUsuarioRegistrado estado,
        String username
) {}
