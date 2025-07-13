package com.afk.backend.control.service;

import com.afk.backend.control.dto.TipoRequisitoDto;

import java.util.List;

public interface TipoRequisitoService {
    TipoRequisitoDto createTipoRequisito(TipoRequisitoDto tipo);
    TipoRequisitoDto findTipoRequisitoById(Long id);
    List<TipoRequisitoDto> findAllTiposRequisito();
    void deleteTipoRequisitoById(Long id);
}
