package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.CitaDto;
import com.afk.backend.control.mapper.CitaMapper;
import com.afk.backend.control.service.CitaService;
import com.afk.backend.model.entity.Cita;
import com.afk.backend.model.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository repository;
    private final CitaMapper mapper;

    public CitaServiceImpl(CitaRepository repository, CitaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CitaDto createCita(CitaDto dto) {
        Cita cita = mapper.toEntity(dto);
        Cita savedCita = repository.save(cita);
        return mapper.toDto(savedCita);
    }

    @Override
    public CitaDto findCitaById(Long id) {
        Cita cita = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Cita con ID " + id + " no encontrada"));
        return mapper.toDto(cita);
    }

    @Override
    public List<CitaDto> findAllCitas() {
        List<Cita> citas = repository.findAll();
        return citas.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CitaDto updateCita(Long id, CitaDto dto) {
        Cita existingCita = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Cita con ID " + id + " no encontrada"));
        mapper.updateEntityFromDto(dto, existingCita);
        Cita updatedCita = repository.save(existingCita);
        return mapper.toDto(updatedCita);
    }

    @Override
    public void deleteCitaById(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Cita con ID " + id + " no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public List<CitaDto> findCitasByUsuario(Long idUsuario) {
        Optional<Cita> citaOptional = repository.findById(idUsuario);
        return citaOptional.map(cita -> List.of(mapper.toDto(cita)))
                .orElseGet(Collections::emptyList);
    }
}
