package com.afk.backend.control.service;

import com.afk.backend.control.dto.DashboardReportesDto;
import java.util.Map;

public interface DashboardReportesService {
    DashboardReportesDto obtenerMetricasReportes(Long empresaId);
    Map<String, Long> obtenerReportesPorTipo(Long empresaId);
    Map<String, Long> obtenerReportesPorSeveridad(Long empresaId);
    Map<String, Long> obtenerReportesPorEstado(Long empresaId);
}