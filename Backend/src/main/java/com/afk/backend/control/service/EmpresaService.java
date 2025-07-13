package com.afk.backend.control.service;

import com.afk.backend.control.dto.EmpresaDto;

import java.util.List;

public interface EmpresaService {
    EmpresaDto createEmpresa(EmpresaDto empresa);
    EmpresaDto findEmpresaById(Long id);
    List<EmpresaDto> findAllEmpresas();
    EmpresaDto updateEmpresa(Long id, EmpresaDto empresa);
    void deleteEmpresaById(Long id);
    List<EmpresaDto> findEmpresasByGerente(Long idUsuario);
    EmpresaDto findEmpresaWithVacantes(Long idEmpresa);
}
