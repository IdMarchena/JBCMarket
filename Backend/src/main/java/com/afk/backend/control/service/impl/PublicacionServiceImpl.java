package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.PublicacionDto;
import com.afk.backend.control.mapper.PublicacionMapper;
import com.afk.backend.control.service.PublicacionService;
import com.afk.backend.model.entity.*;
import com.afk.backend.model.entity.enm.EstadoPublicacion;
import com.afk.backend.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final VacanteRepository vacanteRepository;
    private final CalificacionRepository calificacionRepository;
    private final PublicacionMapper mapper;

    @Override
    @Transactional
    public PublicacionDto createPublicacion(PublicacionDto publicacionDto) {
        Vacante vacante = vacanteRepository.findById(publicacionDto.idVacante())
                .orElseThrow(() -> new RuntimeException("Vacante no encontrada"));
        Publicacion publicacion = mapper.toEntity(publicacionDto);
        publicacion.setTitulo(publicacionDto.titulo());
        publicacion.setDescripcion(publicacionDto.descripcion());
        publicacion.setVacante(vacante);
        publicacion.setFechaPublicacion(LocalDateTime.now());
        publicacion.setEstadoPublicacion(EstadoPublicacion.valueOf(publicacionDto.estadoPublicacion()));

        // Verificar si hay calificaciones asociadas
        if (publicacionDto.calificaciones() != null && !publicacionDto.calificaciones().isEmpty()) {
            // Solo buscar calificaciones si la lista no está vacía
            List<Calificacion> calificaciones = calificacionRepository.findByPublicacion_Id(publicacionDto.id());
            publicacion.setCalificaciones(calificaciones);
        } else {
            // Si no hay calificaciones, inicializa la lista vacía para evitar null
            publicacion.setCalificaciones(List.of());
        }

        Publicacion savedPublicacion = publicacionRepository.save(publicacion);
        return mapper.toDto(savedPublicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public PublicacionDto findPublicacionById(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        return mapper.toDto(publicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicacionDto> findAllPublicaciones() {
        return mapper.toDtoList(publicacionRepository.findAll());
    }

    @Override
    @Transactional
    public PublicacionDto updatePublicacion(Long id, PublicacionDto publicacionDto) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        mapper.updateEntityFromDto(publicacion, publicacionDto);

        if (publicacionDto.titulo() != null) {
            publicacion.setTitulo(publicacionDto.titulo());
        }
        if (publicacionDto.descripcion() != null) {
            publicacion.setDescripcion(publicacionDto.descripcion());
        }
        if (publicacionDto.estadoPublicacion() != null) {
            publicacion.setEstadoPublicacion(EstadoPublicacion.valueOf(publicacionDto.estadoPublicacion()));
        }

        Publicacion updatedPublicacion = publicacionRepository.save(publicacion);
        return mapper.toDto(updatedPublicacion);
    }

    @Override
    @Transactional
    public void deletePublicacionById(Long id) {
        if (!publicacionRepository.existsById(id)) {
            throw new RuntimeException("Publicación no encontrada");
        }
        publicacionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicacionDto> findByVacanteId(Long idVacante) {
        return mapper.toDtoList(publicacionRepository.findByVacanteId(idVacante));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicacionDto> findByEmpresaId(Long idEmpresa) {
        return mapper.toDtoList(publicacionRepository.findByVacanteEmpresaId(idEmpresa));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicacionDto> findByRequisitosUsuarioRegistrado(Long idUsuario) {
        return mapper.toDtoList(publicacionRepository.findByRequisitosUsuarioRegistrado(idUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Integer obtenerCantidadPublicacionesEmpresa(Long idEmpresa){
        return publicacionRepository.countByPublicacionEmpresaId(idEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicacionDto> searchPostByFilter(String filtro, Pageable pageable){
        Page<Publicacion> publicacions = publicacionRepository.findByTituloContaining(filtro, pageable);
        return publicacions.map(mapper::toDto);

    }
}