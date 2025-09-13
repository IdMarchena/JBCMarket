package com.afk.backend.control.service;

import com.afk.backend.control.dto.EmpresaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmpresaService {
    EmpresaDto createEmpresa(EmpresaDto empresa);
    EmpresaDto findEmpresaById(Long id);
    List<EmpresaDto> findAllEmpresas();
    EmpresaDto updateEmpresa(Long id, EmpresaDto empresa);
    void deleteEmpresaById(Long id);
    List<EmpresaDto> findEmpresasByGerente(Long idUsuario);
    Integer obtenerCantidadEmpresas();
    Integer obtenerCantidadEmpresaPorGerente(Long idGerente);
    Page<EmpresaDto> buscarEmpresasFiltro(String filtro, Pageable pageable);
    Page<EmpresaDto> searchByEmpresaOrRequisito(String keyword,Pageable pageable);
}
