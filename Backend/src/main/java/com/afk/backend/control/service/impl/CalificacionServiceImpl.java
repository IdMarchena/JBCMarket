package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.CalificacionDto;
import com.afk.backend.control.mapper.CalificacionMapper;
import com.afk.backend.control.service.CalificacionService;
import com.afk.backend.model.entity.Calificacion;
import com.afk.backend.model.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository repository;
    private final CalificacionMapper mapper;

    public CalificacionServiceImpl(CalificacionRepository repository,
                                   @Qualifier("calificacionMapperImpl") CalificacionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CalificacionDto createCalificacion(CalificacionDto dto) {
        Calificacion calificacion = mapper.toEntity(dto);
        Calificacion savedCalificacion = repository.save(calificacion);
        return mapper.toDto(savedCalificacion);
    }

    @Override
    public CalificacionDto findCalificacionById(Long id) {
        Calificacion calificacion = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Calificación con ID " + id + " no encontrada"));
        return mapper.toDto(calificacion);
    }

    @Override
    public List<CalificacionDto> findAllCalificaciones() {
        List<Calificacion> calificaciones = repository.findAll();
        return calificaciones.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CalificacionDto updateCalificacion(Long id, CalificacionDto dto) {
        Calificacion existingCalificacion = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Calificación con ID " + id + " no encontrada"));
        mapper.updateEntityFromDto(dto, existingCalificacion);
        Calificacion updatedCalificacion = repository.save(existingCalificacion);
        return mapper.toDto(updatedCalificacion);
    }

    @Override
    public void deleteCalificacionById(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Calificación con ID " + id + " no encontrada");
        }
        repository.deleteById(id);
    }
}
