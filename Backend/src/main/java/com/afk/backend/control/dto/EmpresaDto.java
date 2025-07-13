package com.afk.backend.control.dto;

import java.util.List;

public record EmpresaDto(
        Long id,
        String nombre,
        String descripcion,
        Long idUsuarioGerente,
        Long idTipoEmpresa,
        Integer numeroEmpleados,
        List<VacanteDto> vacantes
) {}