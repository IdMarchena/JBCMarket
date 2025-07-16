package com.afk.backend.control.controller;

import com.afk.backend.control.dto.UsuarioRegistradoDto;
import com.afk.backend.control.service.UsuarioRegistradoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios-registrados")
@RequiredArgsConstructor
public class UsuarioRegistradoController {

    private final UsuarioRegistradoService usuarioRegistradoService;

    @PostMapping
    public ResponseEntity<UsuarioRegistradoDto> crearUsuarioRegistrado(@RequestBody UsuarioRegistradoDto dto) {
        UsuarioRegistradoDto creado = usuarioRegistradoService.createUsuarioRegistrado(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRegistradoDto> obtenerUsuarioRegistradoPorId(@PathVariable Long id) {
        UsuarioRegistradoDto dto = usuarioRegistradoService.findUsuarioRegistradoById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRegistradoDto>> listarUsuariosRegistrados() {
        List<UsuarioRegistradoDto> lista = usuarioRegistradoService.findAllUsuariosRegistrados();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuarioRegistrado(@PathVariable Long id) {
        usuarioRegistradoService.deleteUsuarioRegistradoById(id);
        return ResponseEntity.noContent().build();
    }
}
