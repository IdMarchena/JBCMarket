package com.afk.backend.control.mapper;

import com.afk.backend.model.entity.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RolMapper {

    @Named("mapRol")
    default Rol map(Long id) {
        if (id == null) {
            return null;
        }
        Rol rol = new Rol();
        rol.setId(id);
        return rol;
    }
}