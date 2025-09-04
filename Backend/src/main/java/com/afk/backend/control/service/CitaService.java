package com.afk.backend.control.service;

import com.afk.backend.client.external.dto.UbicacionDt;
import com.afk.backend.control.dto.CitaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {
    CitaDto createCita(CitaDto cita);
    CitaDto findCitaById(Long id);
    List<CitaDto> findAllCitas();
    CitaDto updateCita(Long id, CitaDto cita);
    void deleteCitaById(Long id);
    List<CitaDto> findCitasByUsuario(Long idUsuario);
    Page<CitaDto> buscarCitas(LocalDateTime fecha, Pageable pageable);
    Integer obtenerCantidadCitas();

}
