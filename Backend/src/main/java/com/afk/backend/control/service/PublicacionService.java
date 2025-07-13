package com.afk.backend.control.service;

import com.afk.backend.control.dto.PublicacionDto;

import java.util.List;

public interface PublicacionService {
    PublicacionDto createPublicacion(PublicacionDto publicacion);
    PublicacionDto findPublicacionById(Long id);
    List<PublicacionDto> findAllPublicaciones();
    PublicacionDto updatePublicacion(Long id, PublicacionDto publicacion);
    void deletePublicacionById(Long id);
    List<PublicacionDto> findByVacanteId(Long idVacante);
    List<PublicacionDto> findByEmpresaId(Long idEmpresa);
}
