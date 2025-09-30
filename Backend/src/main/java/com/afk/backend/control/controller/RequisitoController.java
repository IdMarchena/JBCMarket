package com.afk.backend.control.controller;

import com.afk.backend.control.dto.RequisitoDto;
import com.afk.backend.control.service.RequisitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requisitos")
@RequiredArgsConstructor
public class RequisitoController {

    private final RequisitoService requisitoService;

    @PostMapping
    public ResponseEntity<RequisitoDto> crearRequisito(@RequestBody RequisitoDto dto) {
        RequisitoDto creado = requisitoService.createRequisito(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequisitoDto> obtenerRequisitoPorId(@PathVariable Long id) {
        RequisitoDto dto = requisitoService.findRequisitoById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<RequisitoDto>> obtenerTodosLosRequisitos() {
        List<RequisitoDto> lista = requisitoService.findAllRequisitos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/vacante/{idVacante}")
    public ResponseEntity<List<RequisitoDto>> obtenerRequisitosPorVacante(@PathVariable Long idVacante) {
        List<RequisitoDto> lista = requisitoService.findRequisitosByVacanteId(idVacante);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRequisito(@PathVariable Long id) {
        requisitoService.deleteRequisitoById(id);
        return ResponseEntity.noContent().build();
    }
}
