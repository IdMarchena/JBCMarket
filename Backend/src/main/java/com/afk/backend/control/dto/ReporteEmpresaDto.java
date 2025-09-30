package com.afk.backend.control.dto;

import java.time.LocalDateTime;

public record ReporteEmpresaDto(
        Long id,
        String descripcion,
        LocalDateTime fechaReporte,
        Long empresaId,
        Long usuarioId,
        String tipoReporte,
        String severidad,
        String estadoResolucion
) {}