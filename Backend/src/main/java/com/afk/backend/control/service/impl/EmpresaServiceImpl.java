package com.afk.backend.control.service.impl;
import com.afk.backend.control.dto.EmpresaDto;
import com.afk.backend.control.mapper.EmpresaMapper;
import com.afk.backend.control.service.EmpresaService;
import com.afk.backend.model.entity.Empresa;
import com.afk.backend.model.entity.TipoEmpresa;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.Vacante;
import com.afk.backend.model.repository.*;
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
    private final UsuarioRepository usuarioRepository;
    private final TipoEmpresaRepository tipoEmpresaRepository;
    private final VacanteRepository vacanteRepository;

    public EmpresaServiceImpl(EmpresaRepository repository,
                              @Qualifier("empresaMapperImpl") EmpresaMapper mapper,
                              UsuarioRepository usuarioRepository,
                              TipoEmpresaRepository tipoEmpresaRepository,
                              VacanteRepository vacanteRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
        this.tipoEmpresaRepository = tipoEmpresaRepository;
        this.vacanteRepository = vacanteRepository;
    }

    @Override
    public EmpresaDto createEmpresa(EmpresaDto dto) {
        Empresa empresa = mapper.toEntity(dto);
        empresa.setNombre(dto.nombre());
        empresa.setDescripcion(dto.descripcion());
        Usuario usuario = usuarioRepository.findUsuarioByIdUsuario(dto.idUsuarioGerente());
        empresa.setUsuario(usuario);
        Optional<TipoEmpresa> tipoEmpresa=tipoEmpresaRepository.findById(dto.idTipoEmpresa());
        if(tipoEmpresa.isPresent()) {
            TipoEmpresa tipo=tipoEmpresa.get();
            empresa.setTipo_Empresa(tipo);
        }

        empresa.setNumeroEmpleados(dto.numeroEmpleados());
        if(dto.vacantes()!=null && !dto.vacantes().isEmpty()) {
            List<Vacante> vacantes = vacanteRepository.findByEmpresa_Id(dto.id());
            empresa.setVacantes(vacantes);
        }else{
            empresa.setVacantes(List.of());
        }
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
        Page<Empresa> empresas= repository.searchByEmpresaOrRequisito(filtro, pageable);
        return empresas.map(mapper::toDto);
    }
}
