package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.EmpresaDto;
import com.afk.backend.control.dto.PublicacionDto;
import com.afk.backend.model.entity.*;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring", uses = {VacanteMapper.class})
@Component
public interface EmpresaMapper {

    @Named("mapUb")
    default Usuario mapUb(Long id){
        if(id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Named("mapT")
    default TipoEmpresa mapT(Long id){
        if(id == null) return null;
        TipoEmpresa tipoEmpresa = new TipoEmpresa();
        tipoEmpresa.setId(id);
        return tipoEmpresa;
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "usuario", source = "idUsuarioGerente", qualifiedByName = "mapUb")
    @Mapping(target = "tipo_Empresa", source = "idTipoEmpresa", qualifiedByName = "mapT")
    @Mapping(target = "numeroEmpleados", source = "numeroEmpleados")
    @Mapping(target = "vacantes", source = "vacantes")
    Empresa toEntity(EmpresaDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "usuario.id", target = "idUsuarioGerente")
    @Mapping(source = "tipo_Empresa.id", target = "idTipoEmpresa")
    @Mapping(source = "numeroEmpleados", target = "numeroEmpleados")
    @Mapping(source = "vacantes", target = "vacantes")
    EmpresaDto toDto(Empresa empresa);

    default List<EmpresaDto> toDtoList(Iterable<Empresa> empresas) {
        return StreamSupport.stream(empresas.spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EmpresaDto dto, @MappingTarget Empresa entity);
}

