package com.afk.backend.control.dto;
import com.afk.backend.model.entity.enm.EsatadoReporteEmpresa;
import java.time.LocalDateTime;

public record ReporteEmpresaDto(
        Long id,
        String descripcion,
        LocalDateTime fechaReporte,
        Long empresaId,
        Long usuarioId,
        String tipoReporte,
        String severidad,
        EsatadoReporteEmpresa estadoResolucion
) {}