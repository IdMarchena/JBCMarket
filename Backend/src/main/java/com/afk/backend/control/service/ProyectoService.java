package com.afk.backend.control.service;

import com.afk.backend.control.dto.ProyectoDto;

import java.util.List;

public interface ProyectoService {
    ProyectoDto createProyecto(ProyectoDto proyecto);
    ProyectoDto findProyectoById(Long id);
    List<ProyectoDto> findAllProyectos();
    ProyectoDto updateProyecto(Long id, ProyectoDto proyecto);
    void deleteProyecto(Long id);
}
