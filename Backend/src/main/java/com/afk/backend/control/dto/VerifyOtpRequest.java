package com.afk.backend.control.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record VerifyOtpRequest(
        @NotBlank(message = "El numero de telefono es obligatorio")
        String phoneNumber,
        @NotBlank(message = "El codigo es obligatorio")
        String otp,
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "La contrasenia es obligatoria")
        String contrasenia,
        @NotBlank(message = "El correo es obligatorio")
        String correo) {
}
