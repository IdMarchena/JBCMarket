package com.afk.backend.control.service;

import com.afk.backend.control.dto.TipoEmpresaDto;

import java.util.List;

public interface TipoEmpresaService {
    TipoEmpresaDto createTipoEmpresa(TipoEmpresaDto tipo);
    TipoEmpresaDto findTipoEmpresaById(Long id);
    List<TipoEmpresaDto> findAllTiposEmpresa();
    void deleteTipoEmpresaById(Long id);
}
