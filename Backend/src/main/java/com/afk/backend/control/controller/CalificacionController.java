package com.afk.backend.control.controller;

import com.afk.backend.control.dto.CalificacionDto;
import com.afk.backend.control.service.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calificaciones")
@RequiredArgsConstructor
public class CalificacionController {

    private final CalificacionService calificacionService;

    @PostMapping
    public ResponseEntity<CalificacionDto> crearCalificacion(@RequestBody CalificacionDto calificacionDto) {
        CalificacionDto creada = calificacionService.createCalificacion(calificacionDto);
        return ResponseEntity.ok(creada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalificacionDto> obtenerPorId(@PathVariable Long id) {
        CalificacionDto calificacion = calificacionService.findCalificacionById(id);
        return ResponseEntity.ok(calificacion);
    }

    @GetMapping
    public ResponseEntity<List<CalificacionDto>> listarTodas() {
        List<CalificacionDto> calificaciones = calificacionService.findAllCalificaciones();
        return ResponseEntity.ok(calificaciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalificacionDto> actualizarCalificacion(@PathVariable Long id, @RequestBody CalificacionDto dto) {
        CalificacionDto actualizada = calificacionService.updateCalificacion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCalificacion(@PathVariable Long id) {
        calificacionService.deleteCalificacionById(id);
        return ResponseEntity.noContent().build();
    }
}
