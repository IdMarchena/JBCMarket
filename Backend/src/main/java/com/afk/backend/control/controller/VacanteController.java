package com.afk.backend.control.controller;

import com.afk.backend.control.dto.VacanteDto;
import com.afk.backend.control.service.VacanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacantes")
@RequiredArgsConstructor
public class VacanteController {

    private final VacanteService vacanteService;

    @PostMapping
    public ResponseEntity<VacanteDto> crearVacante(@RequestBody VacanteDto vacanteDto) {
        VacanteDto creado = vacanteService.createVacante(vacanteDto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacanteDto> obtenerVacantePorId(@PathVariable Long id) {
        VacanteDto vacante = vacanteService.findVacanteById(id);
        return ResponseEntity.ok(vacante);
    }

    @GetMapping
    public ResponseEntity<List<VacanteDto>> listarVacantes() {
        List<VacanteDto> vacantes = vacanteService.findAllVacantes();
        return ResponseEntity.ok(vacantes);
    }

    @GetMapping("/gerente/{idGerente}")
    public ResponseEntity<List<VacanteDto>> listarVacantesPorGerente(@PathVariable Long idGerente) {
        List<VacanteDto> vacantes = vacanteService.findVacantesByGerenteId(idGerente);
        return ResponseEntity.ok(vacantes);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<VacanteDto>> listarVacantesPorEmpresa(@PathVariable Long idEmpresa) {
        List<VacanteDto> vacantes = vacanteService.findVacantesByEmpresaId(idEmpresa);
        return ResponseEntity.ok(vacantes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVacante(@PathVariable Long id) {
        vacanteService.deleteVacanteById(id);
        return ResponseEntity.noContent().build();
    }
}
