package com.afk.backend.model.repository;

import com.afk.backend.model.entity.ReporteEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface ReporteEmpresaRepository extends JpaRepository<ReporteEmpresa, Long> {

    Page<ReporteEmpresa> findByEmpresaId(Long empresaId, Pageable pageable);

    List<ReporteEmpresa> findByFechaReporteAfter(LocalDateTime fecha);

    @Query("SELECT COUNT(r) FROM ReporteEmpresa r WHERE r.empresa.id = :empresaId")
    Long countByEmpresa(Long empresaId);

    @Query("SELECT COUNT(r) FROM ReporteEmpresa r WHERE r.empresa.id = :empresaId AND r.estado_reporte = :estado")
    Long countByEmpresaAndEstadoResolucion(Long empresaId, String estado);

    @Query("SELECT COUNT(r) FROM ReporteEmpresa r WHERE r.empresa.id = :empresaId AND r.fechaReporte > :fecha")
    Long countByEmpresaAndFechaReporteAfter(Long empresaId, LocalDateTime fecha);

    @Query("SELECT r.tipo_reporte, COUNT(r) FROM ReporteEmpresa r WHERE r.empresa.id = :empresaId GROUP BY r.tipo_reporte")
    List<Object[]> countByTipoReporteAndEmpresa(Long empresaId);

    @Query("SELECT r.severidad, COUNT(r) FROM ReporteEmpresa r WHERE r.empresa.id = :empresaId GROUP BY r.severidad")
    List<Object[]> countBySeveridadAndEmpresa(Long empresaId);

    @Query("SELECT r.estado_reporte, COUNT(r) FROM ReporteEmpresa r WHERE r.empresa.id = :empresaId GROUP BY r.estado_reporte")
    List<Object[]> countByEstadoResolucionAndEmpresa(Long empresaId);
}