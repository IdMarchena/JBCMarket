package com.afk.backend.control.service;

import com.afk.backend.control.dto.PostulacionDto;

import java.util.List;

public interface PostulacionService {
    PostulacionDto createPostulacion(PostulacionDto postulacion);
    PostulacionDto findPostulacionById(Long id);
    List<PostulacionDto> findAllPostulaciones();
    PostulacionDto updatePostulacion(Long id, PostulacionDto postulacion);
    void deletePostulacionById(Long id);
    List<PostulacionDto> findPostulacionesByVacante(Long idVacante);
    List<PostulacionDto> findPostulacionesByUsuario(Long idUsuario);
}
