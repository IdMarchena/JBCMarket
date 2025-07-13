package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.DashboardReportesDto;
import com.afk.backend.control.service.DashboardReportesService;
import com.afk.backend.model.repository.ReporteEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardReportesServiceImpl implements DashboardReportesService {

    private final ReporteEmpresaRepository reporteRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardReportesDto obtenerMetricasReportes(Long empresaId) {
        Long totalReportes = reporteRepository.countByEmpresa(empresaId);
        Long reportesAbiertos = reporteRepository.countByEmpresaAndEstadoResolucion(empresaId, "ABIERTO");
        Long reportesResueltos = reporteRepository.countByEmpresaAndEstadoResolucion(empresaId, "RESUELTO");
        Long reportesUltimoMes = reporteRepository.countByEmpresaAndFechaReporteAfter(empresaId, LocalDateTime.now().minusMonths(1));

        Map<String, Long> reportesPorTipo = reporteRepository.countByTipoReporteAndEmpresa(empresaId)
                .stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1]
                ));

        Map<String, Long> reportesPorSeveridad = reporteRepository.countBySeveridadAndEmpresa(empresaId)
                .stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1]
                ));

        return new DashboardReportesDto(
                totalReportes,
                reportesAbiertos,
                reportesResueltos,
                reportesUltimoMes,
                reportesPorTipo,
                reportesPorSeveridad
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obtenerReportesPorTipo(Long empresaId) {
        return reporteRepository.countByTipoReporteAndEmpresa(empresaId)
                .stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1]
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obtenerReportesPorSeveridad(Long empresaId) {
        return reporteRepository.countBySeveridadAndEmpresa(empresaId)
                .stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1]
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obtenerReportesPorEstado(Long empresaId) {
        return reporteRepository.countByEstadoResolucionAndEmpresa(empresaId)
                .stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1]
                ));
    }
}