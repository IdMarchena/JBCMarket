package com.afk.backend.control.service;

import com.afk.backend.control.dto.OfertaLaboralDto;

import java.util.List;

public interface OfertaLaboralService {
    OfertaLaboralDto createOferta(OfertaLaboralDto oferta);
    OfertaLaboralDto findOfertaById(Long id);
    List<OfertaLaboralDto> findAllOfertas();
    void deleteOfertaById(Long id);
}
