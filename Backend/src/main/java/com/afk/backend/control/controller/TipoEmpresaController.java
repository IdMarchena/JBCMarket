package com.afk.backend.control.controller;

import com.afk.backend.control.dto.TipoEmpresaDto;
import com.afk.backend.control.service.TipoEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-empresa")
@RequiredArgsConstructor
public class TipoEmpresaController {

    private final TipoEmpresaService tipoEmpresaService;

    @PostMapping
    public ResponseEntity<TipoEmpresaDto> crearTipoEmpresa(@RequestBody TipoEmpresaDto tipoEmpresaDto) {
        TipoEmpresaDto creado = tipoEmpresaService.createTipoEmpresa(tipoEmpresaDto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEmpresaDto> obtenerTipoEmpresaPorId(@PathVariable Long id) {
        TipoEmpresaDto tipoEmpresa = tipoEmpresaService.findTipoEmpresaById(id);
        return ResponseEntity.ok(tipoEmpresa);
    }

    @GetMapping
    public ResponseEntity<List<TipoEmpresaDto>> listarTiposEmpresa() {
        List<TipoEmpresaDto> tipos = tipoEmpresaService.findAllTiposEmpresa();
        return ResponseEntity.ok(tipos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoEmpresa(@PathVariable Long id) {
        tipoEmpresaService.deleteTipoEmpresaById(id);
        return ResponseEntity.noContent().build();
    }
}
