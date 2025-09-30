package com.afk.backend.control.controller;

import com.afk.backend.control.dto.DashboardReportesDto;
import com.afk.backend.control.service.DashboardReportesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard-reportes")
@RequiredArgsConstructor
public class DashboardReportesController {

    private final DashboardReportesService dashboardService;

    @GetMapping("/{empresaId}/metricas")
    public ResponseEntity<DashboardReportesDto> obtenerMetricas(@PathVariable Long empresaId) {
        return ResponseEntity.ok(dashboardService.obtenerMetricasReportes(empresaId));
    }

    @GetMapping("/{empresaId}/por-tipo")
    public ResponseEntity<Map<String, Long>> obtenerPorTipo(@PathVariable Long empresaId) {
        return ResponseEntity.ok(dashboardService.obtenerReportesPorTipo(empresaId));
    }

    @GetMapping("/{empresaId}/por-severidad")
    public ResponseEntity<Map<String, Long>> obtenerPorSeveridad(@PathVariable Long empresaId) {
        return ResponseEntity.ok(dashboardService.obtenerReportesPorSeveridad(empresaId));
    }

    @GetMapping("/{empresaId}/por-estado")
    public ResponseEntity<Map<String, Long>> obtenerPorEstado(@PathVariable Long empresaId) {
        return ResponseEntity.ok(dashboardService.obtenerReportesPorEstado(empresaId));
    }
}
