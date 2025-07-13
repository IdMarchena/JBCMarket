package com.afk.backend.control.dto;

import com.afk.backend.model.entity.Ubicacion;
import com.afk.backend.model.entity.enm.EstadoUbicacion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionDto {
    private Long id_ubicacion;
    private Ubicacion id_padre;
    private String nombre;
    private Double latitud;
    private Double longitud;
    @Enumerated(EnumType.STRING)
    private EstadoUbicacion estado;
}
