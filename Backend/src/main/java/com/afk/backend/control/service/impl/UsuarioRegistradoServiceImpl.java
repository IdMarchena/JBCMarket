package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.UsuarioRegistradoDto;
import com.afk.backend.control.mapper.UsuarioRegistradoMapper;
import com.afk.backend.control.service.UsuarioRegistradoService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioRegistradoServiceImpl implements UsuarioRegistradoService {

    private final UsuarioRegistradoRepository usuarioRegistradoRepository;
    private final RolRepository rolRepository;
    private final UbicacionRepository ubicacionRepository;
    @Qualifier("usuarioRegistradoMapperImpl")
    private final UsuarioRegistradoMapper mapper;

    @Override
    @Transactional
    public UsuarioRegistradoDto createUsuarioRegistrado(UsuarioRegistradoDto usuarioDto) {

        if (usuarioDto.rolId() != null) {
            Rol rol = rolRepository.findById(usuarioDto.rolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        }


        Ubicacion ubicacion = ubicacionRepository.findById(usuarioDto.ubicacionId())
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));


        UsuarioRegistrado usuario = mapper.toEntity(usuarioDto);


        if (usuarioDto.rolId() != null) {
            usuario.setRol(rolRepository.getReferenceById(usuarioDto.rolId()));
        }
        usuario.setUbicacion(ubicacion);


        UsuarioRegistrado savedUsuario = usuarioRegistradoRepository.save(usuario);

        return mapper.toDto(savedUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioRegistradoDto findUsuarioRegistradoById(Long id) {
        UsuarioRegistrado usuario = usuarioRegistradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapper.toDto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioRegistradoDto> findAllUsuariosRegistrados() {
        List<UsuarioRegistrado> usuarios = usuarioRegistradoRepository.findAll();
        return mapper.toDtoList(usuarios);
    }

    @Override
    @Transactional
    public void deleteUsuarioRegistradoById(Long id) {
        if (!usuarioRegistradoRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRegistradoRepository.deleteById(id);
    }
}