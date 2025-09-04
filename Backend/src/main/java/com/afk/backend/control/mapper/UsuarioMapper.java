package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.UsuarioDto;
import com.afk.backend.model.entity.Perfil;
import com.afk.backend.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

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

    @Named("mapP")
    default Perfil mapP(Long id) {
        if(id == null) return null;
        Perfil perfil = new Perfil();
        perfil.setId(id);
        return perfil;
    }

    @Named("perfilToId")
    default Long perfilToId(Perfil perfil) {
        if (perfil == null) return null;
        return perfil.getId();
    }

    @Mapping(target = "perfil", source = "idPerfil", qualifiedByName = "mapP")
    Usuario toEntity(UsuarioDto UsuarioDto);

    @Mapping(source = "perfil", target = "idPerfil", qualifiedByName = "perfilToId")
    UsuarioDto toDto(Usuario ususario);

    List<UsuarioDto> toListDto(List<Usuario> ususarios);

    List<Usuario> toListEntity(List<UsuarioDto> UsuariosDto);
}

