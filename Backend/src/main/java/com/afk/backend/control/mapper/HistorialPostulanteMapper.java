package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.HistorialPostulanteDto;
import com.afk.backend.model.entity.HistorialPostulante;
import com.afk.backend.model.entity.Postulacion;
import com.afk.backend.model.entity.Usuario;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        imports = {LocalDateTime.class},
        uses = {UsuarioMapper.class})
public interface HistorialPostulanteMapper {

    default Postulacion mapP(Long id){
        if(id == null) return null;
        Postulacion postulacion = new Postulacion();
        postulacion.setId(id);
        return postulacion;
    }

    default Usuario mapU(Long id){
        if(id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postulacion", expression = "java(mapP(dto.idPostulacion()))")
    @Mapping(target = "usuario", expression = "java(mapU(dto.idUsuarioPostulante()))")
    @Mapping(target = "fecha_historial_postulante", expression = "java(LocalDateTime.now())")
    HistorialPostulante toEntity(HistorialPostulanteDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "idPostulacion", source = "postulacion.id")
    @Mapping(target = "idUsuarioPostulante", source = "usuario.id")
    @Mapping(target = "fechaRegistro", source = "fecha_historial_postulante")
    HistorialPostulanteDto toDto(HistorialPostulante historial);

    List<HistorialPostulanteDto> toDtoList(List<HistorialPostulante> historiales);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(HistorialPostulanteDto dto, @MappingTarget HistorialPostulante entity);
}