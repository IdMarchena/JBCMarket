package com.afk.backend.control.service;

import com.afk.backend.control.dto.VacanteDto;

import java.util.List;

public interface VacanteService {
    VacanteDto createVacante(VacanteDto vacante);
    VacanteDto findVacanteById(Long id);
    List<VacanteDto> findAllVacantes();
    List<VacanteDto> findVacantesByGerenteId(Long idGerente);
    List<VacanteDto> findVacantesByEmpresaId(Long idEmpresa); // ✅ NUEVO
    void deleteVacanteById(Long id);
}

