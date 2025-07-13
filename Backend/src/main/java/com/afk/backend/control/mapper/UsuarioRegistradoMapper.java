package com.afk.backend.control.mapper;
import com.afk.backend.control.dto.UsuarioRegistradoDto;
import com.afk.backend.control.dto.UsuarioRegistradoResponse;
import com.afk.backend.model.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = { UbicacionMapper.class, RolMapper.class})
public interface UsuarioRegistradoMapper {
    @Named("mapUbicacion")
    default Ubicacion map(Long id) {
        if (id == null) {
            return null;
        }
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setId(id);
        return ubicacion;
    }
    @Mapping(target = "id", source = "id")
    @Mapping(target = "rol", source = "rolId", qualifiedByName = "mapRol")
    @Mapping(target = "ubicacion", source = "ubicacionId",qualifiedByName = "mapUbicacion")
    @Mapping(target = "fecha_registro", expression = "java(LocalDateTime.now())")
    @Mapping(target = "telefono_usuario", source = "telefono")
    @Mapping(target = "estado_usuario_registrado", source = "estado")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "correo", source = "email")
    @Mapping(target = "contrasenia", source = "password")
    UsuarioRegistrado toEntity(UsuarioRegistradoDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "rolId", source = "rol.id")
    @Mapping(target = "rolNombre", source = "rol.role")
    @Mapping(target = "ubicacionId", source = "ubicacion.id")
    @Mapping(target = "telefono", source = "telefono_usuario")
    @Mapping(target = "fechaRegistro", source = "fecha_registro")
    @Mapping(target = "estado", source = "estado_usuario_registrado")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "email", source = "correo")
    @Mapping(target = "password", source = "contrasenia")
    UsuarioRegistradoDto toDto(UsuarioRegistrado usuario);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "rolNombre", source = "rol.role")
    @Mapping(target = "ubicacionNombre", source = "ubicacion.nombre")
    @Mapping(target = "telefono", source = "telefono_usuario")
    @Mapping(target = "fechaRegistro", source = "fecha_registro")
    @Mapping(target = "estado", source = "estado_usuario_registrado")
    UsuarioRegistradoResponse toResponse(UsuarioRegistrado usuario);

    List<UsuarioRegistradoDto> toDtoList(List<UsuarioRegistrado> usuarios);
    List<UsuarioRegistradoResponse> toResponseList(List<UsuarioRegistrado> usuarios);
}