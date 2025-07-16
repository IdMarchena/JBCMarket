package com.afk.backend.control.controller;

import com.afk.backend.control.dto.CreateRequest;
import com.afk.backend.control.dto.HistorialResponse;
import com.afk.backend.control.dto.UsuarioRolDto;
import com.afk.backend.control.service.UsuarioRolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-roles")
@RequiredArgsConstructor
public class UsuarioRolController {

    private final UsuarioRolService usuarioRolService;

    @PostMapping
    public ResponseEntity<UsuarioRolDto> crearUsuarioRol(@RequestBody CreateRequest request) {
        UsuarioRolDto creado = usuarioRolService.createUsuarioRol(request);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRolDto> obtenerUsuarioRolPorId(@PathVariable Long id) {
        UsuarioRolDto dto = usuarioRolService.findUsuarioRolById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRolDto>> listarUsuarioRoles() {
        List<UsuarioRolDto> lista = usuarioRolService.findAllUsuarioRoles();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<List<HistorialResponse>> historialRolesPorUsuario(@PathVariable Long usuarioId) {
        List<HistorialResponse> historial = usuarioRolService.findHistorialRolesByUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/activo/{usuarioId}")
    public ResponseEntity<UsuarioRolDto> rolActivoPorUsuario(@PathVariable Long usuarioId) {
        UsuarioRolDto rolActivo = usuarioRolService.findRolActivoByUsuario(usuarioId);
        return ResponseEntity.ok(rolActivo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuarioRol(@PathVariable Long id) {
        usuarioRolService.deleteUsuarioRolById(id);
        return ResponseEntity.noContent().build();
    }
}
