package com.afk.backend.control.controller;

import com.afk.backend.control.dto.EmpresaDto;
import com.afk.backend.control.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaDto> createEmpresa(@RequestBody EmpresaDto empresaDto) {
        EmpresaDto createdEmpresa = empresaService.createEmpresa(empresaDto);
        return ResponseEntity.ok(createdEmpresa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> getEmpresaById(@PathVariable Long id) {
        EmpresaDto empresaDto = empresaService.findEmpresaById(id);
        return ResponseEntity.ok(empresaDto);
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDto>> getAllEmpresas() {
        List<EmpresaDto> empresas = empresaService.findAllEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> updateEmpresa(
            @PathVariable Long id,
            @RequestBody EmpresaDto empresaDto) {
        EmpresaDto updatedEmpresa = empresaService.updateEmpresa(id, empresaDto);
        return ResponseEntity.ok(updatedEmpresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresaById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gerente/{idUsuario}")
    public ResponseEntity<List<EmpresaDto>> getEmpresasByGerente(@PathVariable Long idUsuario) {
        List<EmpresaDto> empresas = empresaService.findEmpresasByGerente(idUsuario);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{idEmpresa}/vacantes")
    public ResponseEntity<EmpresaDto> getEmpresaWithVacantes(@PathVariable Long idEmpresa) {
        EmpresaDto empresaDto = empresaService.findEmpresaWithVacantes(idEmpresa);
        return ResponseEntity.ok(empresaDto);
    }
}