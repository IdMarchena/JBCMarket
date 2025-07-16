package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.control.mapper.UsuarioMapper;
import com.afk.backend.control.service.UsuarioService;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
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

        return mapToDto(savedUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto findUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToDto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> findAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
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
        return mapToDto(updatedUsuario);
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
        return mapToDto(usuario);
    }

    private UsuarioDto mapToDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                null
        );
    }
}