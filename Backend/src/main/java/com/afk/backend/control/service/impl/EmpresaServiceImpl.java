package com.afk.backend.control.service.impl;
import com.afk.backend.control.dto.EmpresaDto;
import com.afk.backend.control.dto.VacanteDto;
import com.afk.backend.control.mapper.EmpresaMapper;
import com.afk.backend.control.mapper.VacanteMapper;
import com.afk.backend.control.service.EmpresaService;
import com.afk.backend.model.entity.Empresa;
import com.afk.backend.model.entity.Vacante;
import com.afk.backend.model.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;
    private final VacanteMapper vacanteMapper;

    public EmpresaServiceImpl(EmpresaRepository repository,
                              @Qualifier("empresaMapperImpl") EmpresaMapper mapper,
                              @Qualifier("vacanteMapperImpl") VacanteMapper vacanteMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.vacanteMapper=vacanteMapper;
    }

    @Override
    public EmpresaDto createEmpresa(EmpresaDto dto) {
        Empresa empresa = mapper.toEntity(dto);
        Empresa savedEmpresa = repository.save(empresa);
        return mapper.toDto(savedEmpresa);
    }


    @Override
    @Transactional(readOnly = true)
    public EmpresaDto findEmpresaById(Long id) {
        Empresa empresa = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Empresa con ID " + id + " no encontrada"));
        return mapper.toDto(empresa);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<EmpresaDto> findEmpresasByGerente(Long idUsuario) {
        Optional<Empresa> empresaOptional = repository.findById(idUsuario);
        return empresaOptional.map(empresa -> List.of(mapper.toDto(empresa)))
                .orElseGet(Collections::emptyList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VacanteDto> findVacantesByEmpresas(Long idEmpresa) {
        List<Vacante> vacantes = repository.findVacantesByUsuarioId(idEmpresa);
        return vacanteMapper.toDtoList(vacantes);
    }

    @Override
    public Integer obtenerCantidadEmpresas(){
        return (int) repository.count();
    }

    @Override
    public Integer obtenerCantidadEmpresaPorGerente(Long idGerente) {
        List<Empresa> empresas = repository.findByUsuarioId(idGerente);
        return empresas.size();
    }
    @Override
    @Transactional(readOnly = true)
    public Page<EmpresaDto> buscarEmpresasFiltro(String filtro, Pageable pageable){
        Page<Empresa> empresas= repository.findByNombreContaining(filtro, pageable);
        return empresas.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpresaDto> searchByEmpresaOrRequisito(String filtro, Pageable pageable){
        Page<Empresa> empresas= repository.findByNombreContaining(filtro, pageable);
        return empresas.map(mapper::toDto);
    }
}
