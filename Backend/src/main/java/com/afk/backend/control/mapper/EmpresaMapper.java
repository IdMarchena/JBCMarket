package com.afk.backend.control.mapper;

import com.afk.backend.control.dto.EmpresaDto;
import com.afk.backend.model.entity.Empresa;
import com.afk.backend.model.entity.Usuario;
import com.afk.backend.model.entity.TipoEmpresa;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface EmpresaMapper {

    default Usuario mapU(Long id){
        if(id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    default TipoEmpresa mapT(Long id){
        if(id == null) return null;
        TipoEmpresa tipoEmpresa = new TipoEmpresa();
        tipoEmpresa.setId(id);
        return tipoEmpresa;
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre",source="nombre")
    @Mapping(target = "descripcion",source="descripcion")
    @Mapping(target = "usuario", expression = "java(mapU(dto.idUsuarioGerente()))")
    @Mapping(target = "tipo_Empresa", expression = "java(mapT(dto.idTipoEmpresa()))")
    @Mapping(target = "numeroEmpleados",source="numeroEmpleados")
    Empresa toEntity(EmpresaDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre",target="nombre")
    @Mapping(source = "descripcion",target="descripcion")
    @Mapping(source = "usuario.id", target = "idUsuarioGerente")
    @Mapping(source = "tipo_Empresa.id", target = "idTipoEmpresa")
    @Mapping(source = "numeroEmpleados",target="numeroEmpleados")
    EmpresaDto toDto(Empresa empresa);





    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EmpresaDto dto,@MappingTarget Empresa entity);

}
