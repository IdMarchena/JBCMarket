package com.afk.backend.control.dto;

public record UsuarioDto(
        Long id,
        String nombre,
        String correo,
        String contrasenia
) {}