package com.afk.backend.control.controller;

import com.afk.backend.control.dto.HistorialPostulanteDto;
import com.afk.backend.control.service.HistorialPostulanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-postulantes")
@RequiredArgsConstructor
public class HistorialPostulanteController {

    private final HistorialPostulanteService historialService;

    @PostMapping
    public ResponseEntity<HistorialPostulanteDto> createHistorial(@RequestBody HistorialPostulanteDto dto) {
        HistorialPostulanteDto created = historialService.createHistorial(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialPostulanteDto> getHistorialById(@PathVariable Long id) {
        HistorialPostulanteDto historial = historialService.findHistorialById(id);
        return ResponseEntity.ok(historial);
    }

    @GetMapping
    public ResponseEntity<List<HistorialPostulanteDto>> getAllHistoriales() {
        List<HistorialPostulanteDto> historiales = historialService.findAllHistoriales();
        return ResponseEntity.ok(historiales);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorial(@PathVariable Long id) {
        historialService.deleteHistorialById(id);
        return ResponseEntity.noContent().build();
    }
}
