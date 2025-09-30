package com.afk.backend.control.dto;
import java.time.LocalDateTime;
import java.util.List;
public record VacanteDto(
        Long id,
        String nombre,
        String descripcion,
        Long idUbicacion,
        Long idEmpresa,
        LocalDateTime fechaVcante,
        List<RequisitoDto> requisitos
) {}
