package com.afk.backend.control.service;

import com.afk.backend.control.dto.UsuarioDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {
    UsuarioDto createUsuario(UsuarioDto usuario);
    UsuarioDto findUsuarioById(Long id);
    List<UsuarioDto> findAllUsuarios();
    UsuarioDto updateUsuario(Long id, UsuarioDto usuario);
    void deleteUsuarioById(Long id);
    UsuarioDto findByCorreo(String correo);
    UsuarioDto findByNombre(String nombre);
    boolean existsByCorreo(String correo);
    Integer getCantidadUsuarios();
    Page<UsuarioDto> SearchUserByFilter(String filtro, Pageable pageable);
}
