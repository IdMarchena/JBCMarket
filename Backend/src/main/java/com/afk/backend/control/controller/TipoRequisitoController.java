package com.afk.backend.control.controller;

import com.afk.backend.control.dto.TipoRequisitoDto;
import com.afk.backend.control.service.TipoRequisitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-requisito")
@RequiredArgsConstructor
public class TipoRequisitoController {

    private final TipoRequisitoService tipoRequisitoService;

    @PostMapping
    public ResponseEntity<TipoRequisitoDto> crearTipoRequisito(@RequestBody TipoRequisitoDto dto) {
        TipoRequisitoDto creado = tipoRequisitoService.createTipoRequisito(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoRequisitoDto> obtenerTipoRequisitoPorId(@PathVariable Long id) {
        TipoRequisitoDto tipoRequisito = tipoRequisitoService.findTipoRequisitoById(id);
        return ResponseEntity.ok(tipoRequisito);
    }

    @GetMapping
    public ResponseEntity<List<TipoRequisitoDto>> listarTiposRequisito() {
        List<TipoRequisitoDto> lista = tipoRequisitoService.findAllTiposRequisito();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoRequisito(@PathVariable Long id) {
        tipoRequisitoService.deleteTipoRequisitoById(id);
        return ResponseEntity.noContent().build();
    }
}
