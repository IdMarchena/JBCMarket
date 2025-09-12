package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.HistorialResponse;
import com.afk.backend.control.dto.UsuarioRegistradoDto;
import com.afk.backend.control.dto.UsuarioRolDto;
import com.afk.backend.control.mapper.UsuarioRolMapper;
import com.afk.backend.control.service.UsuarioRolService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioRolServiceImpl implements UsuarioRolService {

    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioRegistradoRepository usuarioRegistradoRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolMapper mapper;

    @Override
    @Transactional
    public UsuarioRolDto createUsuarioRol(UsuarioRolDto usuarioRolDto) {
        UsuarioRegistrado usuario = usuarioRegistradoRepository.findById(usuarioRolDto.idUsuarioRegistrado())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(usuarioRolDto.idRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuarioRolRepository.findByUsuarioRegistradoAndEstadoUsuarioRol(usuario, EstadoUsuarioRol.ASIGNADO)
                .ifPresent(rolActual -> {
                    rolActual.setEstadoUsuarioRol(EstadoUsuarioRol.ELIMINADO);
                    rolActual.setFecha_fin_rol(LocalDateTime.now());
                    usuarioRolRepository.save(rolActual);
                });

        UsuarioRol usuarioRol = mapper.toEntity(usuarioRolDto);
        usuarioRol.setUsuarioRegistrado(usuario);
        usuarioRol.setRol(rol);

        UsuarioRol savedUsuarioRol = usuarioRolRepository.save(usuarioRol);
        return mapper.toDto(savedUsuarioRol);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioRolDto findUsuarioRolById(Long id) {
        UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioRol no encontrado"));
        return mapper.toDto(usuarioRol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioRolDto> findAllUsuarioRoles() {
        return mapper.toDtoList(usuarioRolRepository.findAll());
    }

    @Override
    public List<UsuarioRolDto> findAllByUsuarioRegistradoAndEstadoUsuarioRol(UsuarioRegistradoDto usuarioRegistradoDto, String estadoUsuarioRol) {
        UsuarioRegistrado usuarioRegistrado = usuarioRegistradoRepository.findById(usuarioRegistradoDto.id()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapper.toDtoList(usuarioRolRepository.findAllByUsuarioRegistradoAndEstadoUsuarioRol(usuarioRegistrado,EstadoUsuarioRol.valueOf(estadoUsuarioRol)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialResponse> findHistorialRolesByUsuario(Long usuarioId) {
        return mapper.toHistorialResponseList(
                usuarioRolRepository.findByUsuarioRegistradoIdOrderByFechaActivacionRolDesc(usuarioId)
        );
    }

    @Override
    @Transactional
    public void deleteUsuarioRolById(Long id) {
        UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioRol no encontrado"));

        usuarioRol.setEstadoUsuarioRol(EstadoUsuarioRol.ELIMINADO);
        usuarioRol.setFecha_fin_rol(LocalDateTime.now());
        usuarioRolRepository.save(usuarioRol);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioRolDto findRolActivoByUsuario(Long usuarioId) {
        UsuarioRegistrado usuario = usuarioRegistradoRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioRol rolActivo = usuarioRolRepository.findByUsuarioRegistradoAndEstadoUsuarioRol(usuario, EstadoUsuarioRol.ACTIVO)
                .orElseThrow(() -> new RuntimeException("No se encontró un rol activo para el usuario"));

        return mapper.toDto(rolActivo);
    }
}