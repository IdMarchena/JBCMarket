package com.afk.backend.control.service.impl;
import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.control.mapper.UsuarioMapper;
import com.afk.backend.control.service.UsuarioService;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        if (usuarioRepository.findByCorreo(usuarioDto.correo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.nombre());
        usuario.setCorreo(usuarioDto.correo());
        usuario.setContrasenia(usuarioDto.contrasenia());

        Usuario savedUsuario = usuarioRepository.save(usuario);

        return usuarioMapper.toDto(savedUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto findUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findByIdWithPerfil(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> findAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAllWithPerfil();
        return usuarios.stream().map(usuarioMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!usuario.getCorreo().equals(usuarioDto.correo())) {
            Optional<Usuario> usuarioConCorreo = usuarioRepository.findByCorreo(usuarioDto.correo());
            if (usuarioConCorreo.isPresent() && !usuarioConCorreo.get().getId().equals(id)) {
                throw new RuntimeException("El correo ya está en uso por otro usuario");
            }
        }

        usuario.setNombre(usuarioDto.nombre());
        usuario.setCorreo(usuarioDto.correo());

        if (usuarioDto.contrasenia() != null && !usuarioDto.contrasenia().isEmpty()) {
            usuario.setContrasenia(usuarioDto.contrasenia()); // En producción, encriptar
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(updatedUsuario);
    }

    @Override
    @Transactional
    public void deleteUsuarioById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto findByCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto findByNombre(String nombre) {
        Optional<Usuario> usuario= usuarioRepository.findByNombre(nombre);
        return usuarioMapper.toDto(usuario.get());
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override

    public boolean existsByNombre(String nombre) {return usuarioRepository.existsByNombre(nombre);}

    @Override
    public Integer getCantidadUsuarios() {
        return  (int) usuarioRepository.count();
    }

    @Override
    public Page<UsuarioDto> SearchUserByFilter(String filtro, Pageable pageable){
        Page<Usuario> usuarios = usuarioRepository.findByCorreoContaining(filtro, pageable);
        return usuarios.map(usuarioMapper::toDto);
    }
}