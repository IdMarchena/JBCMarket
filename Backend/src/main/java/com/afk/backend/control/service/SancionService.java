package com.afk.backend.control.service;

import com.afk.backend.control.dto.SancionDto;

import java.util.List;

public interface SancionService {
    SancionDto createSancion(SancionDto sancion);
    SancionDto findSancionById(Long id);
    List<SancionDto> findAllSanciones();
    void deleteSancionById(Long id);
}
