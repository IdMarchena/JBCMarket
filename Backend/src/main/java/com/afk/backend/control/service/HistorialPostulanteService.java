package com.afk.backend.control.service;

import com.afk.backend.control.dto.HistorialPostulanteDto;

import java.util.List;

public interface HistorialPostulanteService {
    HistorialPostulanteDto createHistorial(HistorialPostulanteDto historial);
    HistorialPostulanteDto findHistorialById(Long id);
    List<HistorialPostulanteDto> findAllHistoriales();
    void deleteHistorialById(Long id);
}
