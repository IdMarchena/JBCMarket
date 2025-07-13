package com.afk.backend.control.service;

import com.afk.backend.control.dto.CitaDto;

import java.util.List;

public interface CitaService {
    CitaDto createCita(CitaDto cita);
    CitaDto findCitaById(Long id);
    List<CitaDto> findAllCitas();
    CitaDto updateCita(Long id, CitaDto cita);
    void deleteCitaById(Long id);
    List<CitaDto> findCitasByUsuario(Long idUsuario);
}
