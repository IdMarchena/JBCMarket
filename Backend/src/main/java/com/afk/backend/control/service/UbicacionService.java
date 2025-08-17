package com.afk.backend.control.service;

import com.afk.backend.client.external.dto.UbicacionDt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UbicacionService {
    Mono<UbicacionDt> obtenerCoordenadas(String direccion);
    Mono<UbicacionDt> getUbicacion(Long id);
    void sincronizarUbicacion(Long id);
    Integer obtenerCantidadUbicaciones();
    List<UbicacionDt> obtenerUbicaciones();
    Page<UbicacionDt> buscarUbicaciones(String filtro, Pageable pageable);
    UbicacionDt createUbicacion(UbicacionDt ubicacion);
    UbicacionDt updateUicacion(Long id, UbicacionDt ubicacion);
    void deleteUbicacion(Long id);
}