package com.afk.backend.control.controller;

import com.afk.backend.control.dto.SancionDto;
import com.afk.backend.control.service.SancionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanciones")
@RequiredArgsConstructor
public class SancionController {

    private final SancionService sancionService;

    @PostMapping
    public ResponseEntity<SancionDto> crearSancion(@RequestBody SancionDto dto) {
        SancionDto creada = sancionService.createSancion(dto);
        return ResponseEntity.ok(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SancionDto> obtenerSancionPorId(@PathVariable Long id) {
        SancionDto dto = sancionService.findSancionById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<SancionDto>> listarSanciones() {
        List<SancionDto> lista = sancionService.findAllSanciones();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSancion(@PathVariable Long id) {
        sancionService.deleteSancionById(id);
        return ResponseEntity.noContent().build();
    }
}
