package com.afk.backend.control.controller;

import com.afk.backend.control.dto.ProyectoDto;
import com.afk.backend.control.service.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {
    private final ProyectoService proyectoService;

    @PostMapping
    public ResponseEntity<ProyectoDto> crearProyecto(@RequestBody ProyectoDto proyectoDto) {
        return ResponseEntity.ok(proyectoService.createProyecto(proyectoDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDto> buscarProyectoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.findProyectoById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProyectoDto>> listarProyectos() {
        return ResponseEntity.ok(proyectoService.findAllProyectos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDto> actualizarProyecto(@PathVariable Long id, @RequestBody ProyectoDto proyectoDto) {
        return ResponseEntity.ok(proyectoService.updateProyecto(id, proyectoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.deleteProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
