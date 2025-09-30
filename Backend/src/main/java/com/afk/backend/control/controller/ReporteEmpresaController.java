package com.afk.backend.control.controller;

import com.afk.backend.control.dto.ReporteEmpresaDto;
import com.afk.backend.control.service.ReporteEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes-empresa")
@RequiredArgsConstructor
public class ReporteEmpresaController {

    private final ReporteEmpresaService reporteEmpresaService;

    @PostMapping
    public ResponseEntity<ReporteEmpresaDto> crearReporte(@RequestBody ReporteEmpresaDto dto) {
        ReporteEmpresaDto creado = reporteEmpresaService.createReporte(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteEmpresaDto> obtenerReportePorId(@PathVariable Long id) {
        ReporteEmpresaDto dto = reporteEmpresaService.findReporteById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ReporteEmpresaDto>> obtenerTodosLosReportes() {
        List<ReporteEmpresaDto> lista = reporteEmpresaService.findAllReportes();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reporteEmpresaService.deleteReporteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<Page<ReporteEmpresaDto>> obtenerReportesPorEmpresa(
            @PathVariable Long empresaId,
            Pageable pageable
    ) {
        Page<ReporteEmpresaDto> pagina = reporteEmpresaService.findReportesByEmpresa(empresaId, pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<ReporteEmpresaDto>> obtenerReportesRecientes(@RequestParam int dias) {
        List<ReporteEmpresaDto> lista = reporteEmpresaService.findReportesRecientes(dias);
        return ResponseEntity.ok(lista);
    }
}
