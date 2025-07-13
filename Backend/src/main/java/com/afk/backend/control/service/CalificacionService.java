package com.afk.backend.control.service;

import com.afk.backend.control.dto.CalificacionDto;

import java.util.List;

public interface CalificacionService {
    CalificacionDto createCalificacion(CalificacionDto calificacion);
    CalificacionDto findCalificacionById(Long id);
    List<CalificacionDto> findAllCalificaciones();
    CalificacionDto updateCalificacion(Long id, CalificacionDto calificacion);
    void deleteCalificacionById(Long id);
}
