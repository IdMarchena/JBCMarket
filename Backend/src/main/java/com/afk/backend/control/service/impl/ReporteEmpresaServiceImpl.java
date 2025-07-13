package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.ReporteEmpresaDto;
import com.afk.backend.control.mapper.ReporteEmpresaMapper;
import com.afk.backend.control.service.ReporteEmpresaService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteEmpresaServiceImpl implements ReporteEmpresaService {

    private final ReporteEmpresaRepository reporteRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReporteEmpresaMapper mapper;

    @Override
    @Transactional
    public ReporteEmpresaDto createReporte(ReporteEmpresaDto reporteDto) {
        Empresa empresa = empresaRepository.findById(reporteDto.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Usuario usuario = usuarioRepository.findById(reporteDto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ReporteEmpresa reporte = mapper.toEntity(reporteDto);
        reporte.setEmpresa(empresa);
        reporte.setUsuario(usuario);
        reporte.setFechaReporte(LocalDateTime.now());

        ReporteEmpresa savedReporte = reporteRepository.save(reporte);
        return mapper.toDto(savedReporte);
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteEmpresaDto findReporteById(Long id) {
        ReporteEmpresa reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        return mapper.toDto(reporte);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteEmpresaDto> findAllReportes() {
        return mapper.toDtoList(reporteRepository.findAll());
    }

    // Nuevos métodos para el dashboard
    @Transactional(readOnly = true)
    public Page<ReporteEmpresaDto> findReportesByEmpresa(Long empresaId, Pageable pageable) {
        return reporteRepository.findByEmpresaId(empresaId, pageable)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<ReporteEmpresaDto> findReportesRecientes(int dias) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        return mapper.toDtoList(reporteRepository.findByFechaReporteAfter(fechaLimite));
    }

    @Override
    @Transactional
    public void deleteReporteById(Long id) {
        if (!reporteRepository.existsById(id)) {
            throw new RuntimeException("Reporte no encontrado");
        }
        reporteRepository.deleteById(id);
    }
}