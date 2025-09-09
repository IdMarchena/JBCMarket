package com.afk.backend.control.dto;
import com.afk.backend.model.entity.enm.Roles;

public record SignUpRequest(Long id,
                            String nombre,
                            String apellido,
                            String direccion,
                            String cel,
                            String correo,
                            String contrasenia,
                            Roles rol) {
}
