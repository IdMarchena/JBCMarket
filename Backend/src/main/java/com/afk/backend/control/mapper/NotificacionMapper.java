package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.NotificacionDto;
import com.afk.backend.model.entity.Notificacion;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.Empresa;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    @Named("mapUsuario")
    default  Usuario mapUsuario(Long id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Named("mapEmpresa")
    default  Empresa mapEmpresa(Long id) {
        if (id == null) return null;
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }

    @Mapping(source = "id_notificacion", target = "id")
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Mapping(source = "estado_notificacion", target = "estado")
    NotificacionDto toDto(Notificacion notificacion);

    @Mapping(target = "id_notificacion", source = "id")
    @Mapping(target = "usuario", source = "idUsuario", qualifiedByName = "mapUsuario")
    @Mapping(target = "empresa", source = "idEmpresa", qualifiedByName = "mapEmpresa")
    @Mapping(target = "estado_notificacion", source = "estado")
    Notificacion toEntity(NotificacionDto notificacionDto);

    List<NotificacionDto> toDtoList(List<Notificacion> notificaciones);
    List<Notificacion> toEntityList(List<NotificacionDto> dtos);


}
