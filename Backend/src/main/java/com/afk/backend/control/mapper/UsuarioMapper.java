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
        if (id == null) return null;  // Si el id es null, no creamos un Perfil.
        Perfil perfil = new Perfil();
        perfil.setId(id);
        return perfil;
    }

    @Named("perfilToId")
    default Long perfilToId(Perfil perfil) {
        return (perfil != null) ? perfil.getId() : null;  // Si el perfil es null, devolvemos null.
    }

    // Mapeo de UsuarioDto a Usuario (cuando tienes un idPerfil)
    @Mapping(target = "perfil", source = "idPerfil", qualifiedByName = "mapP")
    Usuario toEntity(UsuarioDto usuarioDto);

    // Mapeo de Usuario a UsuarioDto (si el perfil es null, no asignamos idPerfil)
    @Mapping(source = "perfil", target = "idPerfil", qualifiedByName = "perfilToId")
    UsuarioDto toDto(Usuario usuario);

    // Mapeo de una lista de Usuarios a una lista de UsuarioDto
    List<UsuarioDto> toListDto(List<Usuario> usuarios);

    // Mapeo de una lista de UsuarioDto a una lista de Usuarios
    List<Usuario> toListEntity(List<UsuarioDto> usuariosDto);
}
