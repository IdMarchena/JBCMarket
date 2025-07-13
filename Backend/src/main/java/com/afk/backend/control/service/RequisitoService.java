package com.afk.backend.control.service;

import com.afk.backend.control.dto.RequisitoDto;

import java.util.List;

public interface RequisitoService {
    RequisitoDto createRequisito(RequisitoDto requisito);
    RequisitoDto findRequisitoById(Long id);
    List<RequisitoDto> findAllRequisitos();
    List<RequisitoDto> findRequisitosByVacanteId(Long idVacante);
    void deleteRequisitoById(Long id);
}
