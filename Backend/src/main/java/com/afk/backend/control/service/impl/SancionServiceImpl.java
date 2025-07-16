package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.SancionDto;
import com.afk.backend.control.mapper.SancionMapper;
import com.afk.backend.control.service.SancionService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SancionServiceImpl implements SancionService {

    private final SancionRepository sancionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;
    @Qualifier("sancionMapperImpl")
    private final SancionMapper mapper;

    @Override
    @Transactional
    public SancionDto createSancion(SancionDto sancionDto) {
        Usuario usuario = usuarioRepository.findById(sancionDto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Publicacion publicacion = publicacionRepository.findById(sancionDto.idPublicacion())
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        Sancion sancion = mapper.toEntity(sancionDto);
        sancion.setUsuario(usuario);
        sancion.setPublicacion(publicacion);

        Sancion savedSancion = sancionRepository.save(sancion);
        return mapper.toDto(savedSancion);
    }

    @Override
    @Transactional(readOnly = true)
    public SancionDto findSancionById(Long id) {
        Sancion sancion = sancionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sanción no encontrada"));
        return mapper.toDto(sancion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SancionDto> findAllSanciones() {
        return mapper.toDtoList(sancionRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteSancionById(Long id) {
        if (!sancionRepository.existsById(id)) {
            throw new RuntimeException("Sanción no encontrada");
        }
        sancionRepository.deleteById(id);
    }
}