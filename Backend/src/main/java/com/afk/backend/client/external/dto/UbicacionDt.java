package com.afk.backend.client.external.dto;

import com.afk.backend.model.entity.enm.EstadoUbicacion;

public record UbicacionDt(Long id_ubicacion,
        Long id_padre,
        String nombre,
        Double latitud,
        Double longitud,
        EstadoUbicacion estado) {
}
