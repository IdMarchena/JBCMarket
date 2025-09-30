package com.afk.backend.control.dto;

import java.util.Map;

public record DashboardReportesDto(
        Long id,
        Long totalReportes,
        Long reportesAbiertos,
        Long reportesResueltos,
        Long reportesUltimoMes,
        Map<String, Long> reportesPorTipo,
        Map<String, Long> reportesPorSeveridad
) {}