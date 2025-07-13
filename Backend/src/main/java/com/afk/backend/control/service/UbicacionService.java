package com.afk.backend.control.service;

import com.afk.backend.client.external.dto.UbicacionDt;
import reactor.core.publisher.Mono;

public interface UbicacionService {
    Mono<UbicacionDt> obtenerCoordenadas(String direccion);
    Mono<UbicacionDt> getUbicacion(Long id);
    void sincronizarUbicacion(Long id);
}