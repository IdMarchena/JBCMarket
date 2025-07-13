package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.ReporteEmpresaDto;
import com.afk.backend.model.entity.Empresa;
import com.afk.backend.model.entity.ReporteEmpresa;
import com.afk.backend.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReporteEmpresaMapper {

    @Named("mapEmpresa")
    default Empresa mapEmpresa(Long id) {
        if(id == null) return null;
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }

    @Named("mapUsuario")
    default Usuario mapUsuario(Long id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Mapping(target = "empresa", source = "empresaId", qualifiedByName = "mapEmpresa")
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "mapUsuario")
    ReporteEmpresa toEntity(ReporteEmpresaDto dto);

    @Mapping(target = "empresaId", source = "empresa.id")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "descripcion", source = "desripncion")
    ReporteEmpresaDto toDto(ReporteEmpresa reporte);

    @Named("mapEmpresaNombre")
    default String mapEmpresaNombre(Empresa empresa) {
        return empresa != null ? empresa.getNombre() : null;
    }

    @Named("mapUsuarioNombre")
    default String mapUsuarioNombre(Usuario usuario) {
        return usuario != null ? usuario.getNombre() : null;
    }

    List<ReporteEmpresaDto> toDtoList(List<ReporteEmpresa> reportes);
}
