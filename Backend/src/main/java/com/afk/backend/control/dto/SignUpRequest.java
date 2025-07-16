package com.afk.backend.control.dto;
import com.afk.backend.model.entity.enm.Roles;

public record SignUpRequest(String nombre,
                            String apellido,
                            String dirección,
                            String cel,
                            String correo,
                            String contrasenia,
                            String codigo,
                            Roles rol) {
}
