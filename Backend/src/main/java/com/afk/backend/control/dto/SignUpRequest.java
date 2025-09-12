package com.afk.backend.control.dto;

public record SignUpRequest(String nombre,
                            String correo,
                            String contrasenia) {
}
