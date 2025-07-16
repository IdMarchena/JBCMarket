package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.PostulacionDto;
import com.afk.backend.control.mapper.PostulacionMapper;
import com.afk.backend.control.service.PostulacionService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostulacionServiceImpl implements PostulacionService {

    private final PostulacionRepository postulacionRepository;
    private final VacanteRepository vacanteRepository;
    private final UsuarioRepository usuarioRepository;
    @Qualifier("postulacionMapperImpl")
    private final PostulacionMapper mapper;

    @Override
    @Transactional
    public PostulacionDto createPostulacion(PostulacionDto postulacionDto) {
        Vacante vacante = vacanteRepository.findById(postulacionDto.idVacante())
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

        Usuario usuarioPostulante = usuarioRepository.findById(postulacionDto.idUsuarioPostulante())
                .orElseThrow(() -> new RuntimeException("Usuario postulante no encontrado"));

        Usuario usuarioGerente = usuarioRepository.findById(postulacionDto.idUsuarioPostulante())
                .orElseThrow(() -> new RuntimeException("Usuario gerente no encontrado"));

        Postulacion postulacion = mapper.toEntity(postulacionDto);
        postulacion.setVacante(vacante);
        postulacion.setUsuario(usuarioPostulante);
        postulacion.setUsuarioGerente(usuarioGerente);

        Postulacion savedPostulacion = postulacionRepository.save(postulacion);
        return mapper.toDto(savedPostulacion);
    }

    @Override
    @Transactional(readOnly = true)
    public PostulacionDto findPostulacionById(Long id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));
        return mapper.toDto(postulacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionDto> findAllPostulaciones() {
        return mapper.toDtoList(postulacionRepository.findAll());
    }

    @Override
    @Transactional
    public PostulacionDto updatePostulacion(Long id, PostulacionDto postulacionDto) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        mapper.updateEntityFromDto(postulacion, postulacionDto);

        if (postulacionDto.contenidoPostulacion() != null) {
            postulacion.setContenido_postulacion(postulacionDto.contenidoPostulacion());
        }
        if (postulacionDto.estadoPostulacion() != null) {
            postulacion.setEstadoPostulacion(postulacionDto.estadoPostulacion());
        }

        Postulacion updatedPostulacion = postulacionRepository.save(postulacion);
        return mapper.toDto(updatedPostulacion);
    }

    @Override
    @Transactional
    public void deletePostulacionById(Long id) {
        if (!postulacionRepository.existsById(id)) {
            throw new RuntimeException("Postulación no encontrada");
        }
        postulacionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionDto> findPostulacionesByVacante(Long idVacante) {
        return mapper.toDtoList(postulacionRepository.findByVacanteId(idVacante));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionDto> findPostulacionesByUsuario(Long idUsuario) {
        return mapper.toDtoList(postulacionRepository.findByUsuarioId(idUsuario));
    }
}