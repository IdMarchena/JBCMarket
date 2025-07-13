package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.CreateRequest;
import com.afk.backend.control.dto.HistorialResponse;
import com.afk.backend.control.dto.UsuarioRolDto;
import com.afk.backend.model.entity.*;
import org.mapstruct.*;

import java.lang.reflect.InvocationTargetException;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {UsuarioRegistradoMapper.class, RolMapper.class})
public interface UsuarioRolMapper {

    @Named("mapUsuarioRegistrado")
    default UsuarioRegistrado mapUsuarioRegistrado(Long id) {
        if (id == null) {
            return null;
        }
        try {
            UsuarioRegistrado usuario = UsuarioRegistrado.class.getDeclaredConstructor().newInstance();
            usuario.setId(id);
            return usuario;
        } catch (InstantiationException | IllegalAccessException |
                 NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Error al crear instancia de UsuarioRegistrado", e);
        }
    }

    @Mapping(target = "id_usuario", ignore = true)  // <-- aquí estaba el error
    @Mapping(target = "usuarioRegistrado", source = "usuarioRegistradoId", qualifiedByName = "mapUsuarioRegistrado")
    @Mapping(target = "rol", source = "rolId", qualifiedByName = "mapRol")
    @Mapping(target = "fechaActivacionRol", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "estadoUsuarioRol", expression = "java(com.afk.backend.model.entity.enm.EstadoUsuarioRol.ACTIVO)")
    @Mapping(target = "fecha_fin_rol", ignore = true)
    UsuarioRol toEntity(CreateRequest dto);

    @Mapping(target = "id", source = "id_usuario")
    @Mapping(target = "idUsuarioRegistrado", source = "usuarioRegistrado.id")
    @Mapping(target = "idRol", source = "rol.id")
    @Mapping(target = "fechaActivacion", source = "fechaActivacionRol")
    @Mapping(target = "estado", source = "estadoUsuarioRol")
    @Mapping(target = "fechaFin", source = "fecha_fin_rol")
    UsuarioRolDto toDto(UsuarioRol usuarioRol);

    @Mapping(target = "rolNombre", source = "rol.role")
    @Mapping(target = "fechaActivacion", source = "fechaActivacionRol")
    HistorialResponse toHistorialResponse(UsuarioRol usuarioRol);

    List<UsuarioRolDto> toDtoList(List<UsuarioRol> usuarioRoles);

    List<HistorialResponse> toHistorialResponseList(List<UsuarioRol> usuarioRoles);
}