package com.afk.backend.control.service;

import com.afk.backend.control.dto.PerfilDto;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PerfilService {
    PerfilDto createPerfil(PerfilDto perfil,MultipartFile dto);
    PerfilDto findPerfilById(Long id);
    List<PerfilDto> findAllPerfil();
    PerfilDto updatePerfil(Long id, PerfilDto perfil,MultipartFile archivo);
    void deletePerfil(Long id);
    PerfilDto agregarImagen(Long idUsuario, MultipartFile archivo);
    void eliminarImagen(Long idUsuario);
    Page<PerfilDto> searchPerfil(String perfil, Pageable pageable);
    Resource obtenerImagen(Long idUsuario);
}
