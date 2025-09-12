package com.afk.backend.control.service;

import com.afk.backend.control.dto.HistorialResponse;
import com.afk.backend.control.dto.UsuarioRegistradoDto;
import com.afk.backend.control.dto.UsuarioRolDto;
import com.afk.backend.model.entity.enm.EstadoUsuarioRol;

import java.util.List;

public interface UsuarioRolService {
    UsuarioRolDto createUsuarioRol(UsuarioRolDto usuarioRol);
    UsuarioRolDto findUsuarioRolById(Long id);
    List<UsuarioRolDto> findAllUsuarioRoles();
    List<UsuarioRolDto> findAllByUsuarioRegistradoAndEstadoUsuarioRol(UsuarioRegistradoDto usuarioRegistradoDto, String estadoUsuarioRol);
    List<HistorialResponse> findHistorialRolesByUsuario(Long usuarioId);
    void deleteUsuarioRolById(Long id);
    UsuarioRolDto findRolActivoByUsuario(Long usuarioId);
}