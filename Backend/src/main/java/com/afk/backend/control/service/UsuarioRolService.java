package com.afk.backend.control.service;

import com.afk.backend.control.dto.CreateRequest;
import com.afk.backend.control.dto.HistorialResponse;
import com.afk.backend.control.dto.UsuarioRolDto;
import java.util.List;

public interface UsuarioRolService {
    UsuarioRolDto createUsuarioRol(CreateRequest usuarioRol);
    UsuarioRolDto findUsuarioRolById(Long id);
    List<UsuarioRolDto> findAllUsuarioRoles();
    List<HistorialResponse> findHistorialRolesByUsuario(Long usuarioId);
    void deleteUsuarioRolById(Long id);
    UsuarioRolDto findRolActivoByUsuario(Long usuarioId);
}