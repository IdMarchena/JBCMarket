package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.FavoritoDto;
import com.afk.backend.model.entity.Favorito;
import com.afk.backend.model.entity.Perfil;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.Publicacion;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FavoritoMapper {
    @Named("mapU")
    default Usuario mapUsuario(Long id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
    @Named("mapP")
    default Publicacion mapPublicacion(Long id) {
        if (id == null) return null;
        Publicacion pub = new Publicacion();
        pub.setId(id);
        return pub;
    }
    @Named("mapPerfil")
    default Perfil mapPerfil(Long id) {
        if (id == null) return null;
        Perfil per = new Perfil();
        per.setId(id);
        return per;
    }

    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "publicacion.id", target = "idPublicacion")
    @Mapping(source="perfil.id",target="idPerfil")
    @Mapping(source = "fecha", target = "fechaFavorito")
    FavoritoDto toDto(Favorito favorito);

    @Mapping(target = "usuario",  source = "idUsuario", qualifiedByName = "mapU")
    @Mapping(target = "publicacion", source = "idPublicacion", qualifiedByName = "mapP")
    @Mapping(target="perfil",source="idPerfil",qualifiedByName = "mapPerfil")
    @Mapping(target="fecha",source="fechaFavorito")
    Favorito toEntity(FavoritoDto favoritoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FavoritoDto dto, @MappingTarget Favorito entity);
}
