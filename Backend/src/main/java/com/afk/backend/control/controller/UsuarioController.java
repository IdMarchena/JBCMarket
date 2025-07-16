package com.afk.backend.control.controller;

import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.control.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> crearUsuario(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto creado = usuarioService.createUsuario(usuarioDto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDto usuario = usuarioService.findUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
        List<UsuarioDto> usuarios = usuarioService.findAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        UsuarioDto actualizado = usuarioService.updateUsuario(id, usuarioDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuarioById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/correo")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorCorreo(@RequestParam String correo) {
        UsuarioDto usuario = usuarioService.findByCorreo(correo);
        return ResponseEntity.ok(usuario);
    }
}
