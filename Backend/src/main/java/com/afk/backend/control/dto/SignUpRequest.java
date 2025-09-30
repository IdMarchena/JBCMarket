package com.afk.backend.control.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record SignUpRequest(
                            @NotBlank(message = "El nombre es obligatorio")
                            String nombre,
                            @NotBlank(message = "El correo es obligatorio")
                            @Email(message = "El correo no tiene un formato válido")
                            String correo,
                            @NotBlank(message = "La contraseña es obligatoria")
                            String contrasenia) {
}
