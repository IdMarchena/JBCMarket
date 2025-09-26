package com.afk.backend.control.service.impl;
import com.afk.backend.control.dto.EmpresaDto;
import com.afk.backend.control.mapper.EmpresaMapper;
import com.afk.backend.control.service.EmpresaService;
import com.afk.backend.control.service.UsuarioService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.repository.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;
    private final UsuarioRepository usuarioRepository;
    private final TipoEmpresaRepository tipoEmpresaRepository;
    private final VacanteRepository vacanteRepository;
    private final UsuarioRolRepository userRolRepository;

    public EmpresaServiceImpl(EmpresaRepository repository,
                              @Qualifier("empresaMapperImpl") EmpresaMapper mapper,
                              UsuarioRepository usuarioRepository,
                              TipoEmpresaRepository tipoEmpresaRepository,
                              VacanteRepository vacanteRepository,
                              UsuarioRolRepository usuarioRolRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
        this.tipoEmpresaRepository = tipoEmpresaRepository;
        this.vacanteRepository = vacanteRepository;
        this.userRolRepository=usuarioRolRepository;
    }

    @Override
    public EmpresaDto createEmpresa(EmpresaDto dto) {
        // Mapeamos el DTO a la entidad Empresa
        Empresa empresa = mapper.toEntity(dto);
        empresa.setNombre(dto.nombre());
        empresa.setDescripcion(dto.descripcion());
        log.debug("Creando empresa. Buscando usuario gerente con ID: {}", dto.idUsuarioGerente());
        // Buscamos el usuario que será el gerente de la empresa
        Usuario usuario = usuarioRepository.findUsuarioByIdUsuario(dto.idUsuarioGerente());

        log.error("Usuario gerente con ID {} no encontrado.", dto.idUsuarioGerente());
        // Validamos que el usuario con el id proporcionado exista
        if (usuario == null) {
            throw new RuntimeException("Usuario gerente con ID " + dto.idUsuarioGerente() + " no encontrado.");
        }
        log.debug("Usuario gerente encontrado: {}", usuario);
        // Comprobamos si el usuario tiene el rol de "Gerente"
        UsuarioRol usuarioRol = userRolRepository.getUsuarioRolByIdUsuario(dto.idUsuarioGerente());

        if (usuarioRol == null ) {
            log.error("El usuario con ID {} no tiene ningún rol asignado.", usuario.getId());
            throw new RuntimeException("El usuario no tiene el rol de Gerente.");
        }


        // Asignamos el usuario gerente a la empresa
        empresa.setUsuario(usuario);

        // Validamos el tipo de empresa si está presente
        Optional<TipoEmpresa> tipoEmpresa = tipoEmpresaRepository.findById(dto.idTipoEmpresa());

        if (tipoEmpresa.isPresent()) {
            TipoEmpresa tipo = tipoEmpresa.get();
            empresa.setTipo_Empresa(tipo);
        } else {
            log.error("Tipo de empresa con ID {} no encontrado.", dto.idTipoEmpresa());
            throw new RuntimeException("Tipo de empresa con ID " + dto.idTipoEmpresa() + " no encontrado.");
        }

        // Asignamos el número de empleados
        empresa.setNumeroEmpleados(dto.numeroEmpleados());

        // Verificamos si existen vacantes para la empresa
        if (dto.vacantes() != null && !dto.vacantes().isEmpty()) {
            List<Vacante> vacantes = vacanteRepository.findByEmpresa_Id(dto.id());
            empresa.setVacantes(vacantes);
        } else {
            empresa.setVacantes(List.of()); // Asignamos una lista vacía si no hay vacantes
        }

        // Guardamos la empresa
        Empresa savedEmpresa = repository.save(empresa);

        // Retornamos el DTO de la empresa guardada
        log.debug("Empresa guardada exitosamente con ID: {}", savedEmpresa.getId());
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
