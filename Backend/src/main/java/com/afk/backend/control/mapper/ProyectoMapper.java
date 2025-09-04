package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.ProyectoDto;
import com.afk.backend.model.entity.Proyecto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProyectoMapper {
    ProyectoDto toDto(Proyecto proyecto);
    Proyecto toEntity(ProyectoDto proyectoDto);
    List<ProyectoDto> toListDto(List<Proyecto> proyectos);
    List<Proyecto> toListEntity(List<ProyectoDto> proyectosDto);
}
