package com.afk.backend.control.service.impl;

import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.client.external.service.UbicacionApiClientService;
import com.afk.backend.control.mapper.UbicacionMapper;
import com.afk.backend.control.service.UbicacionService;
import com.afk.backend.model.repository.UbicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService {

    private final UbicacionApiClientService apiClient;
    private final UbicacionRepository ubicacionRepository;
    private final UbicacionMapper ubicacionMapper;

    @Override
    public Mono<UbicacionDt> obtenerCoordenadas(String direccion) {
        return apiClient.obtenerCoordenadas(direccion);
    }

    @Override
    public Mono<UbicacionDt> getUbicacion(Long id) {

        return apiClient.obtenerUbicacionPorId(id);
    }

    @Override
    public void sincronizarUbicacion(Long id) {
        apiClient.obtenerUbicacionPorId(id)
                .map(ubicacionMapper::toEntity)
                .doOnNext(ubicacionRepository::save)
                .subscribe();
    }
}