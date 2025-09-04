package com.afk.backend.control.service.impl;

import com.afk.backend.control.dto.ProyectoDto;
import com.afk.backend.control.mapper.ProyectoMapper;
import com.afk.backend.control.service.ProyectoService;
import com.afk.backend.model.entity.Proyecto;
import com.afk.backend.model.repository.ProyectoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProyectoServiceImpl implements ProyectoService {
    private final ProyectoMapper proyectoMapper;
    private final ProyectoRepository proyectoRepository;

    @Override
    public ProyectoDto createProyecto(ProyectoDto proyectoDto) {
        Proyecto proyecto = new Proyecto(proyectoDto.id(), proyectoDto.urlFoto(), proyectoDto.descripcion());
        return proyectoMapper.toDto(proyectoRepository.save(proyecto));
    }

    @Override
    public ProyectoDto findProyectoById(Long id) {
        return proyectoMapper.toDto(proyectoRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe el proyecto con id: " + id)));
    }

    @Override
    public List<ProyectoDto> findAllProyectos() {
        return proyectoMapper.toListDto(proyectoRepository.findAll());
    }

    @Override
    public ProyectoDto updateProyecto(Long id, ProyectoDto proyecto){
        Proyecto proyectoViejo = proyectoRepository.findById(id).orElseThrow(()->new RuntimeException("No encontrado un proyecto con el id: " + id));
        proyectoViejo.setDescripcion(proyecto.descripcion());
        proyectoViejo.setUrlFoto(proyecto.urlFoto());
        return proyectoMapper.toDto(proyectoRepository.save(proyectoViejo));
    }

    @Override
    public void deleteProyecto(Long id) {
        if(!proyectoRepository.existsById(id)){
            throw new RuntimeException("No existe el proyecto con el id: " + id);
        }
        proyectoRepository.deleteById(id);
    }
}
