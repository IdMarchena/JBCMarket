package com.afk.backend.control.mapper;
import com.afk.backend.control.dto.ProyectoDto;
import com.afk.backend.control.security.service.FileEncoder;
import com.afk.backend.model.entity.Perfil;
import com.afk.backend.model.entity.Proyecto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProyectoMapper {

    @Named("mapPerfilToEntity")
    default Perfil mapPerfilToEntity(Long id) {
        if (id == null) return null;
        Perfil perfil = new Perfil();
        perfil.setId(id);
        return perfil;
    }
    @Named("encodeFile")
    default String encodeFile(String path) {
        if (path == null || path.isBlank()) {
            return null;
        }
        return FileEncoder.encodeFileToBase64(path);
    }


    @Mapping(target = "urlFoto", source = "urlFoto", qualifiedByName = "encodeFile") // <- codifica aquí
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "perfil", source = "idPerfil", qualifiedByName = "mapPerfilToEntity")  // <-- Aquí está la clave
    Proyecto toEntity(ProyectoDto proyectoDto);

    @Mapping(source = "urlFoto", target = "urlFoto", qualifiedByName = "encodeFile") // <- codifica aquí también
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "perfil.id", target = "idPerfil") // <-- Si deseas mapear el id de Perfil en lugar de Perfil completo
    ProyectoDto toDto(Proyecto proyecto);

    @Named("toListDto")
    List<ProyectoDto> toListDto(List<Proyecto> proyectos);

    List<Proyecto> toListEntity(List<ProyectoDto> proyectosDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProyectoDto dto, @MappingTarget Proyecto entity);
}

