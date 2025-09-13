package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record UsuarioRegistradoResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String rolNombre,
        String ubicacionNombre,
        LocalDateTime fechaRegistro,
        String estado,
        String username
) {}
