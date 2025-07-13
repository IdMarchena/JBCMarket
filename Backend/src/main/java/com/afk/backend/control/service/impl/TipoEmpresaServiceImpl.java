package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.TipoEmpresaDto;
import com.afk.backend.control.mapper.TipoEmpresaMapper;
import com.afk.backend.control.service.TipoEmpresaService;
import com.afk.backend.model.entity.TipoEmpresa;
import com.afk.backend.model.repository.TipoEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoEmpresaServiceImpl implements TipoEmpresaService {

    private final TipoEmpresaRepository tipoEmpresaRepository;
    private final TipoEmpresaMapper mapper;

    @Override
    @Transactional
    public TipoEmpresaDto createTipoEmpresa(TipoEmpresaDto tipoEmpresaDto) {
        TipoEmpresa tipoEmpresa = mapper.toEntity(tipoEmpresaDto);
        TipoEmpresa savedTipoEmpresa = tipoEmpresaRepository.save(tipoEmpresa);
        return mapper.toDto(savedTipoEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoEmpresaDto findTipoEmpresaById(Long id) {
        TipoEmpresa tipoEmpresa = tipoEmpresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de empresa no encontrado"));
        return mapper.toDto(tipoEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoEmpresaDto> findAllTiposEmpresa() {
        return mapper.toDtoList(tipoEmpresaRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteTipoEmpresaById(Long id) {
        if (!tipoEmpresaRepository.existsById(id)) {
            throw new RuntimeException("Tipo de empresa no encontrado");
        }
        tipoEmpresaRepository.deleteById(id);
    }
}