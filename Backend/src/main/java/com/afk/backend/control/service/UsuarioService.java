package com.afk.backend.control.service;

import com.afk.backend.control.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {
    UsuarioDto createUsuario(UsuarioDto usuario);
    UsuarioDto findUsuarioById(Long id);
    List<UsuarioDto> findAllUsuarios();
    UsuarioDto updateUsuario(Long id, UsuarioDto usuario);
    void deleteUsuarioById(Long id);
    UsuarioDto findByCorreo(String correo);
}
