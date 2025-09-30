package com.afk.backend.control.service;

import com.afk.backend.control.dto.TipoEmpresaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipoEmpresaService {
    Integer getCuantityCompany();
    TipoEmpresaDto createTipoEmpresa(TipoEmpresaDto tipo);
    TipoEmpresaDto findTipoEmpresaById(Long id);
    List<TipoEmpresaDto> findAllTiposEmpresa();
    void deleteTipoEmpresaById(Long id);
    Page<TipoEmpresaDto> searchTipoEmpresa(String filtro, Pageable pageable);
    TipoEmpresaDto updateTipoEmpresa(Long id,TipoEmpresaDto tipo);
}
