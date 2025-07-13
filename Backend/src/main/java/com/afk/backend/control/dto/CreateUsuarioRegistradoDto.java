package com.afk.backend.control.dto;

public record CreateUsuarioRegistradoDto(
        String nombre,
        String email,
        String telefono,
        Long rolId,
        Long ubicacionId,
        String username,
        String password
) {}
