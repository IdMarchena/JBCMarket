package com.afk.backend.control.service;

import com.afk.backend.control.dto.VacanteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacanteService {
    VacanteDto createVacante(VacanteDto vacante);
    VacanteDto findVacanteById(Long id);
    List<VacanteDto> findAllVacantes();
    List<VacanteDto> findVacantesByGerenteId(Long idGerente);
    List<VacanteDto> findVacantesByEmpresaId(Long idEmpresa);
    List<VacanteDto> findVacantesByNombre(String nombre);
    void deleteVacanteById(Long id);
    Page<VacanteDto> findByNombreContaining(String nombre, Pageable pageable);
    Integer countVacantesByEmpresaId(Long idEmpresa);
    VacanteDto updateVacante(Long id, VacanteDto vacante);
}

