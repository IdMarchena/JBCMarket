package com.afk.backend.control.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record SignUpPhoneRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "El telefono es obligatorio")
        String telefono,
        @NotBlank(message = "La contrasenia es obligatoria")
        String contrasenia
) {
}
