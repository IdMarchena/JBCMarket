package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.TipoRequisitoDto;
import com.afk.backend.control.mapper.TipoRequisitoMapper;
import com.afk.backend.control.service.TipoRequisitoService;
import com.afk.backend.model.entity.TipoRequisito;
import com.afk.backend.model.repository.TipoRequistoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoRequisitoServiceImpl implements TipoRequisitoService {

    private final TipoRequistoRepository tipoRequisitoRepository;
    @Qualifier("tipoRequisitoMapperImpl")
    private final TipoRequisitoMapper mapper;

    @Override
    @Transactional
    public TipoRequisitoDto createTipoRequisito(TipoRequisitoDto tipoRequisitoDto) {
        TipoRequisito tipoRequisito = mapper.toEntity(tipoRequisitoDto);
        TipoRequisito savedTipoRequisito = tipoRequisitoRepository.save(tipoRequisito);
        return mapper.toDto(savedTipoRequisito);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoRequisitoDto findTipoRequisitoById(Long id) {
        TipoRequisito tipoRequisito = tipoRequisitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de requisito no encontrado"));
        return mapper.toDto(tipoRequisito);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoRequisitoDto> findAllTiposRequisito() {
        return mapper.toDtoList(tipoRequisitoRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteTipoRequisitoById(Long id) {
        if (!tipoRequisitoRepository.existsById(id)) {
            throw new RuntimeException("Tipo de requisito no encontrado");
        }
        tipoRequisitoRepository.deleteById(id);
    }
}