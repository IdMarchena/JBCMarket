package com.afk.backend.control.service;
import com.afk.backend.control.dto.CalificacionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CalificacionService {
    CalificacionDto createCalificacion(CalificacionDto calificacion);
    CalificacionDto findCalificacionById(Long id);
    List<CalificacionDto> findAllCalificaciones();
    CalificacionDto updateCalificacion(Long id, CalificacionDto calificacion);
    void deleteCalificacionById(Long id);
    Integer obtenerCantidadCalificaciones();
    Page<CalificacionDto> buscarCalificaciones(String filtro, Pageable pageable);
}
