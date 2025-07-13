package com.afk.backend.control.mapper;

import com.afk.backend.model.entity.Rol;
import com.afk.backend.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Set;

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
}
