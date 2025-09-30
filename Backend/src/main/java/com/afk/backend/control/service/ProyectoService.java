package com.afk.backend.control.service;

import com.afk.backend.control.dto.ProyectoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProyectoService {
    ProyectoDto createProyecto(ProyectoDto proyecto, MultipartFile archivo);
    ProyectoDto findProyectoById(Long id);
    List<ProyectoDto> findAllProyectos();
    ProyectoDto updateProyecto(Long id, ProyectoDto proyecto,MultipartFile archivo);
    void deleteProyecto(Long id);
    Integer cuantityProyectoByIdPerfil(Long id);
    List<ProyectoDto> findAllProyectosByUsuario(Long id);

}
