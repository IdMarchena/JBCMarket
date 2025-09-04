package com.afk.backend.control.controller;

import com.afk.backend.control.dto.CalificacionDto;
import com.afk.backend.control.dto.EmpresaDto;
import com.afk.backend.control.dto.VacanteDto;
import com.afk.backend.control.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping("/createEmpreas")
    public ResponseEntity<EmpresaDto> createEmpresa(@RequestBody EmpresaDto empresaDto) {
        EmpresaDto createdEmpresa = empresaService.createEmpresa(empresaDto);
        return ResponseEntity.ok(createdEmpresa);
    }

    @GetMapping("/obtenerEmpresaBy/{id}")
    public ResponseEntity<EmpresaDto> getEmpresaById(@PathVariable Long id) {
        EmpresaDto empresaDto = empresaService.findEmpresaById(id);
        return ResponseEntity.ok(empresaDto);
    }

    @GetMapping("/obtenerTodasLasEmpresas")
    public ResponseEntity<List<EmpresaDto>> getAllEmpresas() {
        List<EmpresaDto> empresas = empresaService.findAllEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @PutMapping("/updateEmpresa/{id}")
    public ResponseEntity<EmpresaDto> updateEmpresa(
            @PathVariable Long id,
            @RequestBody EmpresaDto empresaDto) {
        EmpresaDto updatedEmpresa = empresaService.updateEmpresa(id, empresaDto);
        return ResponseEntity.ok(updatedEmpresa);
    }

    @DeleteMapping("/deleteEmpresa/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresaById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/obtenerEmpresabygerente/{idGerente}")
    public ResponseEntity<List<EmpresaDto>> getEmpresasByGerente(@PathVariable Long idGerente) {
        List<EmpresaDto> empresas = empresaService.findEmpresasByGerente(idGerente);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/obtenerVacantesDeUnaEmpresa/{idEmpresa}")
    public ResponseEntity<List<VacanteDto>> getEmpresaWithVacantes(@PathVariable Long idEmpresa) {
        List<VacanteDto> vacantes = empresaService.findVacantesByEmpresas(idEmpresa);
        return ResponseEntity.ok(vacantes);
    }
        @GetMapping("/obtenerCantidadEmpresas")
    public ResponseEntity<String> getCantidadEmpresas() {
        return ResponseEntity.ok("La cantidad de empresas es"+ empresaService.obtenerCantidadEmpresas());
    }

    @GetMapping("/obtenerEmpresaPorGerente")
        public ResponseEntity<String> getEmpresaPorGerente(@RequestParam Long idGerente) {
        return ResponseEntity.ok("el gerente con id:"+idGerente+"tiene"+empresaService.obtenerCantidadEmpresaPorGerente(idGerente)+"empresas");
    }

    @GetMapping("/buscarEmpresas")
    public ResponseEntity<Page<EmpresaDto>> buscarEmpresas(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<EmpresaDto> resultados= empresaService.buscarEmpresasFiltro(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscarVacantesKeyWord")
    public ResponseEntity<Page<EmpresaDto>> buscarVacantesKeyWord(
            @RequestParam String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page,
                size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<EmpresaDto> resultados= empresaService.searchByEmpresaOrRequisito(filtro,pageable);
        return ResponseEntity.ok(resultados);
    }

}