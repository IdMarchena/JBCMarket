package com.afk.backend.control.mapper;
import com.afk.backend.control.dto.PerfilDto;
import com.afk.backend.control.security.service.FileEncoder;
import com.afk.backend.model.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    @Named("fromId")
    default Usuario fromId(Long id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Named("toId")
    default Long toId(Usuario usuario) {
        if (usuario == null) return null;
        return usuario.getId();
    }
    @Named("encodeFile")
    default String encodeFile(String path) {
        if (path == null || path.isBlank()) {
            return null;
        }
        return FileEncoder.encodeFileToBase64(path);
    }

    // DTO → Entity
    @Mapping(target = "perfilName", source = "perfilName")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "proyectos", source = "proyectos")
    @Mapping(target = "urlForto", source = "urlForto", qualifiedByName = "encodeFile")
    @Mapping(target = "usuario", source = "idUsuario", qualifiedByName = "fromId") // <--- este es el correcto
    Perfil toEntity(PerfilDto dto);

    // Entity → DTO
    @Mapping(source = "perfilName", target = "perfilName")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "urlForto", target = "urlForto", qualifiedByName = "encodeFile")
    @Mapping(source = "proyectos", target = "proyectos")
    @Mapping(source = "usuario", target = "idUsuario", qualifiedByName = "toId") // <--- este es el correcto
    PerfilDto toDto(Perfil perfil);


    List<PerfilDto> toListDto(List<Perfil> perfiles);
    List<Perfil> toListEntity(List<PerfilDto> perfilesDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PerfilDto dto, @MappingTarget Perfil entity);
}

