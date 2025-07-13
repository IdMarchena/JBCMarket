package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.PostulacionDto;
import com.afk.backend.model.entity.Postulacion;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.Vacante;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring",
        uses = {UsuarioMapper.class, VacanteMapper.class})
public interface PostulacionMapper {

    @Named("mapVacante")
    default Vacante mapV(Long id){
        if(id == null) return null;
        Vacante vacante = new Vacante();
        vacante.setId(id);
        return vacante;
    }

    @Named("mapUsuario")
    default Usuario mapU(Long id){
        if(id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Mapping(target = "vacante", source = "idVacante", qualifiedByName = "mapVacante")
    @Mapping(target = "usuario", source = "idUsuarioPostulante", qualifiedByName = "mapUsuario")
    @Mapping(target = "usuarioGerente", source = "idUsuarioGerente", qualifiedByName = "mapUsuario")
    @Mapping(target = "fechaInicioPostulacion", expression = "java(parseFecha(dto.fechaInicioPostulacion()))")
    @Mapping(target = "fechafincioPostulacion", expression = "java(parseFecha(dto.fechaFinPostulacion()))")
    Postulacion toEntity(PostulacionDto dto);

    @Mapping(target = "idVacante", source = "vacante.id")
    @Mapping(target = "idUsuarioPostulante", source = "usuario.id")
    @Mapping(target = "idUsuarioGerente", source = "usuarioGerente.id")
    @Mapping(target = "fechaInicioPostulacion", source = "fechaInicioPostulacion")
    @Mapping(target = "fechaFinPostulacion", source = "fechafincioPostulacion")
    PostulacionDto toDto(Postulacion postulacion);

    default List<PostulacionDto> toDtoList(Iterable<Postulacion> postulaciones) {
        return StreamSupport.stream(postulaciones.spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vacante", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "usuarioGerente", ignore = true)
    void updateEntityFromDto(@MappingTarget Postulacion entity, PostulacionDto dto);

    default String parseFecha(LocalDateTime fecha) {
        return fecha != null ? fecha.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }

    default LocalDateTime parseFecha(String fecha) {
        return fecha != null ? LocalDateTime.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }
}