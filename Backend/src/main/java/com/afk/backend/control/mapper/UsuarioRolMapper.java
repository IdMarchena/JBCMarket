package com.afk.backend.control.mapper;

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

    @Mapping(target = "id_usuario", source = "id", ignore = true)  // <-- aquí estaba el error
    @Mapping(target = "usuarioRegistrado", source = "idUsuarioRegistrado", qualifiedByName = "mapUsuarioRegistrado")
    @Mapping(target = "rol", source = "idRol", qualifiedByName = "mapRol")
    @Mapping(target = "fechaActivacionRol", source = "fechaActivacion")
    @Mapping(target = "estadoUsuarioRol", source = "estadoNombre")
    @Mapping(target = "fecha_fin_rol", source = "fechaFin", ignore = true)
    UsuarioRol toEntity(UsuarioRolDto dto);

    @Mapping(target = "id", source = "id_usuario")
    @Mapping(target = "idUsuarioRegistrado", source = "usuarioRegistrado.id")
    @Mapping(target = "idRol", source = "rol.id")
    @Mapping(target = "fechaActivacion", source = "fechaActivacionRol")
    @Mapping(target = "estadoNombre", source = "estadoUsuarioRol")
    @Mapping(target = "fechaFin", source = "fecha_fin_rol")
    UsuarioRolDto toDto(UsuarioRol usuarioRol);

    @Mapping(target = "rolNombre", source = "rol.role")
    @Mapping(target = "fechaActivacion", source = "fechaActivacionRol")
    HistorialResponse toHistorialResponse(UsuarioRol usuarioRol);

    List<UsuarioRolDto> toDtoList(List<UsuarioRol> usuarioRoles);

    List<HistorialResponse> toHistorialResponseList(List<UsuarioRol> usuarioRoles);
}