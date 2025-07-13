package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.CitaDto;
import com.afk.backend.model.entity.Cita;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.Postulacion;
import com.afk.backend.model.entity.Empresa;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CitaMapper {

    @Named("mapU")
    default Usuario mapU(Long id){
        if(id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
    @Named("mapP")
    default Postulacion mapP(Long id){
        if(id == null) return null;
        Postulacion postulacion = new Postulacion();
        postulacion.setId(id);
        return postulacion;
    }
    @Named("mapE")
    default Empresa mapE(Long id){
        if(id == null) return null;
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }


    @Mapping(source = "usuario.id", target = "idUsuarioPostulante")
    @Mapping(source = "postulacion.id", target = "idPostulacion")
    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Mapping(source = "estado_cita", target = "estadoCita")
    @Mapping(source = "fecha", target = "fecha")
    CitaDto toDto(Cita cita);


    @Mapping(target = "usuario", source = "idUsuarioPostulante", qualifiedByName = "mapU")
    @Mapping(target = "postulacion", source = "idPostulacion", qualifiedByName = "mapP")
    @Mapping(target = "empresa", source = "idEmpresa", qualifiedByName = "mapE")
    @Mapping(source = "estadoCita", target = "estado_cita")
    @Mapping(target = "fecha", expression = "java(citaDto.fecha() != null ? citaDto.fecha().toLocalDate() : null)")
    Cita toEntity(CitaDto citaDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CitaDto dto, @MappingTarget Cita entity);
}
