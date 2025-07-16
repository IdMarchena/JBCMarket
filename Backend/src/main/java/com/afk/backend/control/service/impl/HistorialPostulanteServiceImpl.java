package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.HistorialPostulanteDto;
import com.afk.backend.control.mapper.HistorialPostulanteMapper;
import com.afk.backend.control.service.HistorialPostulanteService;
import com.afk.backend.model.entity.HistorialPostulante;
import com.afk.backend.model.entity.Postulacion;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.repository.HistorialPostulanteRepository;
import com.afk.backend.model.repository.PostulacionRepository;
import com.afk.backend.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorialPostulanteServiceImpl implements HistorialPostulanteService {

    private final HistorialPostulanteRepository historialRepository;
    private final PostulacionRepository postulacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Qualifier("historialPostulanteMapperImpl")
    private final HistorialPostulanteMapper mapper;

    @Override
    @Transactional
    public HistorialPostulanteDto createHistorial(HistorialPostulanteDto historialDto) {

        Postulacion postulacion = postulacionRepository.findById(historialDto.idPostulacion())
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        Usuario usuario = usuarioRepository.findById(historialDto.idUsuarioPostulante())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        HistorialPostulante historial = mapper.toEntity(historialDto);
        historial.setPostulacion(postulacion);
        historial.setUsuario(usuario);

        HistorialPostulante savedHistorial = historialRepository.save(historial);
        return mapper.toDto(savedHistorial);
    }

    @Override
    @Transactional(readOnly = true)
    public HistorialPostulanteDto findHistorialById(Long id) {
        HistorialPostulante historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        return mapper.toDto(historial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialPostulanteDto> findAllHistoriales() {
        return mapper.toDtoList(historialRepository.findAll());
    }

    @Override
    @Transactional
    public void deleteHistorialById(Long id) {
        if (!historialRepository.existsById(id)) {
            throw new RuntimeException("Historial no encontrado");
        }
        historialRepository.deleteById(id);
    }
}