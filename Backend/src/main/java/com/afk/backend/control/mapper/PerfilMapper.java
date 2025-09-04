package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.PerfilDto;
import com.afk.backend.model.entity.HistorialPostulante;
import com.afk.backend.model.entity.Perfil;
import com.afk.backend.model.entity.Proyecto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface PerfilMapper {
    default List<Proyecto> mapP(List<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Proyecto h = new Proyecto();
            h.setId(id);
            return h;
        }).collect(Collectors.toList());
    }

    @Mapping(target="perfilName",source="perfilName")
    @Mapping(target="descripcion",source="descripcion")
    @Mapping(target = "proyectos", expression = "java(mapP(dto.isdProyectos()))")
    @Mapping(target="urlForto",source="urlForto")
    Perfil toEntity(PerfilDto dto);

    PerfilDto toDto(Perfil perfil);



    List<PerfilDto> toListDto(List<Perfil> perfiles);
    List<Perfil> toListEntity(List<PerfilDto> perfilesDto);
}
