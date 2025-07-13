package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.SancionDto;
import com.afk.backend.model.entity.Publicacion;
import com.afk.backend.model.entity.Sancion;
import com.afk.backend.model.entity.Usuario;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, PublicacionMapper.class})
public interface SancionMapper {

    default Usuario mapUsuario(Long id){
        if(id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    default Publicacion mapPublicacion(Long id){
        if(id == null) return null;
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        return publicacion;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", source = "idUsuario", qualifiedByName = "mapUsuario")
    @Mapping(target = "publicacion", source = "idPublicacion", qualifiedByName = "mapPublicacion")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "fecha_sancion", expression = "java(LocalDateTime.now())")
    @Mapping(target = "estado_sancion", source = "estadoSancion")
    Sancion toEntity(SancionDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "idUsuario", source = "usuario.id")
    @Mapping(target = "idPublicacion", source = "publicacion.id")
    @Mapping(target = "fechaSancion", source = "fecha_sancion")
    @Mapping(target = "estadoSancion", source = "estado_sancion")
    SancionDto toDto(Sancion sancion);

    default List<SancionDto> toDtoList(Iterable<Sancion> sanciones) {
        return StreamSupport.stream(sanciones.spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Named("mapUsuario")
    default Usuario mapUsuarioById(Long id) {
        return mapUsuario(id);
    }

    @Named("mapPublicacion")
    default Publicacion mapPublicacionById(Long id) {
        return mapPublicacion(id);
    }
}