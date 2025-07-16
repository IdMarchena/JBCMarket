package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.RequisitoDto;
import com.afk.backend.control.mapper.RequisitoMapper;
import com.afk.backend.control.service.RequisitoService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequisitoServiceImpl implements RequisitoService {

    private final RequistoRepository requisitoRepository;
    private final VacanteRepository vacanteRepository;
    private final TipoRequistoRepository tipoRequisitoRepository;
    @Qualifier("requisitoMapperImpl")
    private final RequisitoMapper mapper;

    @Override
    @Transactional
    public RequisitoDto createRequisito(RequisitoDto requisitoDto) {
        Vacante vacante = vacanteRepository.findById(requisitoDto.idVacante())
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));

        TipoRequisito tipoRequisito = tipoRequisitoRepository.findById(requisitoDto.idTipoRequisito())
                .orElseThrow(() -> new RuntimeException("Tipo de requisito no encontrado"));

        Requisito requisito = mapper.toEntity(requisitoDto);
        requisito.setVacante(vacante);
        requisito.setTipo_requisito(tipoRequisito);

        Requisito savedRequisito = requisitoRepository.save(requisito);
        return mapper.toDto(savedRequisito);
    }

    @Override
    @Transactional(readOnly = true)
    public RequisitoDto findRequisitoById(Long id) {
        Requisito requisito = requisitoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisito no encontrado"));
        return mapper.toDto(requisito);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequisitoDto> findAllRequisitos() {
        return mapper.toDtoList(requisitoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequisitoDto> findRequisitosByVacanteId(Long idVacante) {
        return mapper.toDtoList(requisitoRepository.findByVacanteId(idVacante));
    }

    @Override
    @Transactional
    public void deleteRequisitoById(Long id) {
        if (!requisitoRepository.existsById(id)) {
            throw new RuntimeException("Requisito no encontrado");
        }
        requisitoRepository.deleteById(id);
    }
}