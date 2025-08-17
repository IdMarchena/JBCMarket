package com.afk.backend.control.service.impl;

import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.client.external.service.UbicacionApiClientService;
import com.afk.backend.control.mapper.UbicacionMapper;
import com.afk.backend.control.service.UbicacionService;
import com.afk.backend.model.entity.Ubicacion;
import com.afk.backend.model.repository.UbicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;


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
    @Override
    public Integer obtenerCantidadUbicaciones() {
        return (int) ubicacionRepository.count();
    }
    @Override
    public List<UbicacionDt> obtenerUbicaciones() {
        return ubicacionMapper.toDtos(ubicacionRepository.findAll());
    }

    @Override
    public Page<UbicacionDt> buscarUbicaciones(String filtro, Pageable pageable){
        Page<Ubicacion> ubicacions= ubicacionRepository.findByNombreContaining(filtro, pageable);
        return ubicacions.map(ubicacionMapper::toDto);
    }
    @Override
    public UbicacionDt createUbicacion(UbicacionDt ubicacion) {
        Ubicacion ubicacionEntity = ubicacionMapper.toEntity(ubicacion);
        Ubicacion ubicacionSaved = ubicacionRepository.save(ubicacionEntity);
        return ubicacionMapper.toDto(ubicacionSaved);
    }
    @Override
    public UbicacionDt updateUicacion(Long id, UbicacionDt ubicacion) {
        Ubicacion existingUbicacion = ubicacionRepository.findById(id).orElseThrow(()->
        new NoSuchElementException("No existe el ubicacion con id " + id));
        ubicacionMapper.updateEntityFromDto(ubicacion, existingUbicacion);
        return ubicacionMapper.toDto(existingUbicacion);
    }
    @Override
    public void deleteUbicacion(Long id) {
        if (!ubicacionRepository.existsById(id)) {
            throw new NoSuchElementException("Calificación con ID " + id + " no encontrada");
        }
        ubicacionRepository.deleteById(id);
    }
}