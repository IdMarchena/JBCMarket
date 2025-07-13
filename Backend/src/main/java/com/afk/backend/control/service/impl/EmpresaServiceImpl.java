package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.CitaDto;
import com.afk.backend.control.dto.EmpresaDto;
import com.afk.backend.control.mapper.EmpresaMapper;
import com.afk.backend.control.service.EmpresaService;
import com.afk.backend.model.entity.Cita;
import com.afk.backend.model.entity.Empresa;
import com.afk.backend.model.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;

    public EmpresaServiceImpl(EmpresaRepository repository, EmpresaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EmpresaDto createEmpresa(EmpresaDto dto) {
        Empresa empresa = mapper.toEntity(dto);
        Empresa savedEmpresa = repository.save(empresa);
        return mapper.toDto(savedEmpresa);
    }

    @Override
    public EmpresaDto findEmpresaById(Long id) {
        Empresa empresa = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Empresa con ID " + id + " no encontrada"));
        return mapper.toDto(empresa);
    }

    @Override
    public List<EmpresaDto> findAllEmpresas() {
        List<Empresa> empresas = repository.findAll();
        return empresas.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EmpresaDto updateEmpresa(Long id, EmpresaDto dto) {
        Empresa existingEmpresa = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Empresa con ID " + id + " no encontrada"));
        mapper.updateEntityFromDto(dto, existingEmpresa);
        Empresa updatedEmpresa = repository.save(existingEmpresa);
        return mapper.toDto(updatedEmpresa);
    }

    @Override
    public void deleteEmpresaById(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Empresa con ID " + id + " no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public List<EmpresaDto> findEmpresasByGerente(Long idUsuario) {
        Optional<Empresa> empresaOptional = repository.findById(idUsuario);
        return empresaOptional.map(empresa -> List.of(mapper.toDto(empresa)))
                .orElseGet(Collections::emptyList);
    }

    @Override
    public EmpresaDto findEmpresaWithVacantes(Long idEmpresa) {
        Empresa empresa = repository.findById(idEmpresa).orElseThrow(() ->
                new NoSuchElementException("Empresa con ID " + idEmpresa + " no encontrada"));
        return mapper.toDto(empresa);
    }
}
