package com.afk.backend.control.service;

import com.afk.backend.control.dto.PublicacionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublicacionService {
    PublicacionDto createPublicacion(PublicacionDto publicacion);
    PublicacionDto findPublicacionById(Long id);
    List<PublicacionDto> findAllPublicaciones();
    PublicacionDto updatePublicacion(Long id, PublicacionDto publicacion);
    void deletePublicacionById(Long id);
    List<PublicacionDto> findByVacanteId(Long idVacante);
    List<PublicacionDto> findByEmpresaId(Long idEmpresa);
    List<PublicacionDto> findByRequisitosUsuarioRegistrado(Long idUsuario);
    Integer obtenerCantidadPublicacionesEmpresa(Long idEmpresa);
    Page<PublicacionDto> searchPostByFilter(String filtro, Pageable pageable);
}
