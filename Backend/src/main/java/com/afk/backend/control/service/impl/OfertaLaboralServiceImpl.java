package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.OfertaLaboralDto;
import com.afk.backend.control.mapper.OfertaLaboralMapper;
import com.afk.backend.control.service.OfertaLaboralService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfertaLaboralServiceImpl implements OfertaLaboralService {

    private final OfertaLaboralRepository ofertaLaboralRepository;
    private final HistorialPostulanteRepository historialPostulanteRepository;
    private final VacanteRepository vacanteRepository;
    private final CalificacionRepository calificacionRepository;
    private final CitaRepository citaRepository;
    private final FavoritoRepository favoritoRepository;
    private final PublicacionRepository publicacionRepository;
    private final EmpresaRepository empresaRepository;
    private final OfertaLaboralMapper mapper;

    @Override
    @Transactional
    public OfertaLaboralDto createOferta(OfertaLaboralDto ofertaDto) {
        OfertaLaboral oferta = mapper.toEntity(ofertaDto);

        if (ofertaDto.idsHistorialPostulantes() != null) {
            List<HistorialPostulante> historiales = historialPostulanteRepository.findAllById(ofertaDto.idsHistorialPostulantes());
            oferta.setHistorial_postulantes(historiales);
        }

        OfertaLaboral savedOferta = ofertaLaboralRepository.save(oferta);
        return mapper.toDto(savedOferta);
    }

    @Override
    @Transactional(readOnly = true)
    public OfertaLaboralDto findOfertaById(Long id) {
        OfertaLaboral oferta = ofertaLaboralRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta laboral no encontrada"));
        return mapper.toDto(oferta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfertaLaboralDto> findAllOfertas() {
        return mapper.toDtoList(ofertaLaboralRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteOfertaById(Long id) {
        if (!ofertaLaboralRepository.existsById(id)) {
            throw new RuntimeException("Oferta laboral no encontrada");
        }
        ofertaLaboralRepository.deleteById(id);
    }
}