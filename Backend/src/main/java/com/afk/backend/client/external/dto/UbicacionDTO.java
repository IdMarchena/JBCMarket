package com.afk.backend.client.external.dto;

import com.afk.backend.model.entity.enm.EstadoUbicacion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionDTO {
    private Long id_ubicacion;
    private Long id_padre;
    private String nombre;
    private Double latitud;
    private Double longitud;
    @Enumerated(EnumType.STRING)
    private EstadoUbicacion estado;
}
