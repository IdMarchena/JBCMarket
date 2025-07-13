package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.PublicacionDto;
import com.afk.backend.model.entity.Calificacion;
import com.afk.backend.model.entity.Publicacion;
import com.afk.backend.model.entity.Vacante;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring",
        uses = {VacanteMapper.class, CalificacionMapper.class})
public interface PublicacionMapper {

    @Named("mapV")
    default Vacante mapV(Long id){
        Vacante vacante = new Vacante();
        vacante.setId(id);
        return vacante;
    }

    @Mapping(target = "calificaciones", ignore = true)
    @Mapping(target = "vacante", source = "idVacante", qualifiedByName = "mapV")
    Publicacion toEntity(PublicacionDto dto);

    @Mapping(target = "idVacante", source = "vacante.id")
    @Mapping(target = "calificacionesIds", expression = "java(mapCalificaciones(publicacion.getCalificaciones()))")
    @Mapping(target = "estadoPublicacion", source = "estado_publiccaion")
    PublicacionDto toDto(Publicacion publicacion);

    default List<PublicacionDto> toDtoList(Iterable<Publicacion> publicaciones) {
        return StreamSupport.stream(publicaciones.spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vacante", ignore = true)
    @Mapping(target = "calificaciones", ignore = true)
    void updateEntityFromDto(@MappingTarget Publicacion entity, PublicacionDto dto);

    default List<Long> mapCalificaciones(List<Calificacion> calificaciones) {
        return calificaciones.stream()
                .map(Calificacion::getId)
                .collect(Collectors.toList());
    }
}